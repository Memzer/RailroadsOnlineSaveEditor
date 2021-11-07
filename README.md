# RailroadsOnlineSaveEditor

This is still very much a work in progress.

To build the JAR file, use "mvn install"

Reading a save can be done as follows:
```
InputStream fis = new FileInputStream(filename);
Save save = SaveReader.read(fis);
```
The save object exposes all parsed properties of the file (eg Players, Splines, Switches, Watertowers etc)
All raw properties in the save file can still be accessed view the getProperties method, but the data is less easy to use this way.

Writing out the save after editing these objects is now supported (Everything except TextProperty types)
```
ByteArrayOutputStream os = SaveWriter.write(save);		
OutputStream fos = new FileOutputStream(filename);
os.writeTo(fos);
```
The API can also generate a .gif of the map, showing track layout:
```
MapRenderer map = new MapRenderer();
BufferedImage image = map.getImage(save);
map.writeImageToDisk(image, new File(filename);
```
