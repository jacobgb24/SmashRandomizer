#### Converting 

Given a character image as a png, run the following commands in sequnce to generate a webp. 
These commands use both imagemagick and cwebp which is a webp converter by Google.

        magick convert <input>.png -resize 1200x1200 -background transparent -gravity center -extent 1200x1200 <output>.png
        cwebp.exe <input>.png -q 100 -o <output>.webp
    
For the `resize`/`extent` arguments, use `1200` for a character portrait and `200` for a character stock icon.

The output should be around `10KB` for icons and `< 300KB` for portraits. Adjust the quality option in cwebp as needed.

<br>

#### File Names
The names of both the portrait and icon files must match. Names are in `lowerCamelCase`. Use the `&` symbol as it is used in Smash. For other special cases, follow the above convention and add a special case into the code.
