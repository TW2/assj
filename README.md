# assj
ASS Subtitles library
---
This library allows you to create an ASS, read an ASS, read a SSA and render events.
There is no release, cause this library is still in alpha, not optimized and tags are, for a lot of them, not implemented.

The render returns a list of BufferedImage which are, in this example, blended. Each image is transparent and includes JavaFx Path(s) elements to improve the speed. A Path is a node where we add others node to modify the global render. Each image represents an event at a nanoseconds time.

You can get an output png like this:
```java
try{
    ASS ass = ASS.Read(args[0]);
    File output = new File(args[1]);
    long nanos = Long.parseLong(args[2]);

    // Initialize JavaFX
    JFXPanel fxPanel = new JFXPanel();

    // Get images
    Platform.runLater(() -> {
        Render r = new Render();
        List<BufferedImage> images = r.getImages(ass, nanos);

        BufferedImage blended = new BufferedImage(
                images.get(0).getWidth(),
                images.get(0).getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = blended.createGraphics();

        for(BufferedImage img : images){
            g2d.drawImage(img, 0, 0, null);
        }

        g2d.dispose();

        try {
            ImageIO.write(blended, "png", output);
        } catch (IOException ex) {
            Logger.getLogger(Assj.class.getName()).log(Level.SEVERE, null, ex);
        }
    });

    Platform.exit();
}catch(NumberFormatException ex){
    Logger.getLogger(Assj.class.getName()).log(Level.SEVERE, null, ex);
}
```

You can play with the demo program by launching the Assj.java main class. A fake ASS is used in memory.

The demo with the use of 'Essai' font:
<img src="https://github.com/TW2/assj/blob/master/screenshots/Capture%20assj%20001.png" />
