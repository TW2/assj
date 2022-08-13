# assj
ASS Subtitles library
---
This library allows you to create an ASS, read an ASS, read a SSA and render events.
There is no release, cause this library is still in alpha and a bit slow with long text. It's not optimized.
Try and crash processing it.

The render returns a list of BufferedImage.
You can get them like this:
```java
// Initialize JavaFX
JFXPanel fxPanel = new JFXPanel();
```
and later:
```java
// Get images
Platform.runLater(() -> {
    TagParameters params = new TagParameters(<your-ASS-object-here>);
    List<BufferedImage> images = params.getImages(<a-nanoseconds-time-here-in-long>);

    // Do what you want with images...
});
```
Images contains transparency and some events if any!

You can play with the demo program by launching the Assj.java main class. A fake ASS is used in memory.

The demo:
<img src="https://github.com/TW2/assj/blob/master/screenshots/Capture%20assj%20001.png" />
