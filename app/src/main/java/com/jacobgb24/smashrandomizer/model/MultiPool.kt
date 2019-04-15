package com.jacobgb24.smashrandomizer.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.jacobgb24.smashrandomizer.R
import kotlin.collections.ArrayList
import java.io.*
import java.lang.Exception

/** State and Logic for handling multiple customized pools
 *
 * Variables:
 * + pools: The list of all user created pools.
 * + activePool: The pool currently used for random character generation.
 *
 * Methods:
 * + newPool(name: String) - creates a new pool and makes it the active pool.
 * + deletePool(index: Int) - deletes a pool at the given index.
 * + selectPool(index: Int) - select a different active pool.
 * + savePools(context: Context) - saves out the pools and current pool to internal storage.
 * + loadPools(context: Context) - loads pools and current pool from internal storage.
 */


lateinit var pools: ArrayList<Pool>
lateinit var activePool: Pool


fun newPool(name: String, baseSelection: Boolean = true) {
    val pool = Pool(name)
    pool.setAllSelection(baseSelection)
    activePool = pool
    pools.add(pool)
}


fun deletePool(index: Int) {
    if (activePool == pools[index]) {
        if (index != 0) {
            selectPool(0)
        }
        else {
            selectPool(1)
        }
    }
    pools.removeAt(index)
}

fun deletePool(pool: Pool) {
    val ind = pools.indexOf(pool)
    if (ind < 0) {
        Log.e("POOL", "Pool not found to delete Expected ${pool.name} of size ${pool.size()}")
        return
    }
    pools.remove(pool)
    if (activePool == pool) {
        when {
            pools.size == 0 -> {
                pools.add(Pool("Default"))
                selectPool(0)
            }
            ind == pools.size -> selectPool(pools.size - 1)
            else -> selectPool(ind)
        }
    }
}


fun selectPool(index: Int) {
    activePool = pools[index]
}


fun savePools(context: Context) {
    val fileOut = FileOutputStream(File(context.filesDir, "pools"))
    val out = ObjectOutputStream(fileOut)

    out.writeObject(pools)
    out.close()
    fileOut.close()

    val sharedPreferences =
        context.getSharedPreferences(context.getString(R.string.selection_file_key), Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val activeIndex = pools.indexOf(activePool)
    editor.putInt("active_pool", activeIndex)
    editor.apply()
}


fun loadPools(context: Context) {
    try {
        val fileIn = FileInputStream(File(context.filesDir, "pools"))
        val input = ObjectInputStream(fileIn)

        // Load up the list of custom pools
        val list = input.readObject() as List<*>
        pools = ArrayList(list.filterIsInstance<Pool>())
        for (pool in pools) {
            pool.checkNewCharacters()
        }

        input.close()
        fileIn.close()
    } catch (e: Exception) {
        pools = ArrayList()
        newPool("Default")
        activePool = pools[0]
        return
    }

    // Set the active pool
    val sharedPreferences =
        context.getSharedPreferences(context.getString(R.string.selection_file_key), Context.MODE_PRIVATE)
    val activeIndex = sharedPreferences.getInt("active_pool", 0)
    val selectedPool = if (activeIndex < pools.size && activeIndex >= 0) activeIndex else 0
    activePool = pools[selectedPool]
}


@SuppressLint("ApplySharedPref")
fun clearPools(context: Context): Boolean {
    val file = File(context.filesDir, "pools")
    context.getSharedPreferences(context.getString(R.string.selection_file_key), Context.MODE_PRIVATE)
        .edit().clear().commit()
    pools = ArrayList()
    newPool("Default")
    activePool = pools[0]
    return file.delete()

}

