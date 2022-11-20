# assj
ASS Subtitles library
---
This library allows you to create an ASS, read an ASS, read a SSA and render events.
There is no release, cause this library is still in alpha, not optimized and tags are, for a lot of them, not implemented.

The render returns a list of BufferedImage which are, in this example, blended. Each image is transparent and includes JavaFx Path(s) elements to improve the speed. A Path is a node where we add others node to modify the global render. Each image represents an event at a nanoseconds time.

You can get an output png like this:
```java
public class BasicUsage {
    
    public static void main(String[] args) {
        try{
            long before = System.currentTimeMillis();

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

            long elapsed = System.currentTimeMillis() - before;
            System.out.println(String.format(
                    "Time elapsed: %fs",
                    AssTime.getLengthInSeconds(AssTime.create(elapsed))
            ));

            Platform.exit();
        }catch(NumberFormatException ex){
            Logger.getLogger(Assj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
```

Or get a BufferedImage with event by calling 'event.getImage()'
```java
public class AdvancedUsage {

    private ASS ass = null;
    private SubEvent controller = new SubEvent();
    
    private long nanosPosition = 0L;
    
    public AdvancedUsage() {
        
        // Listen
        controller.addSubsListener(new SubsListener() {
            @Override
            public void getImage(SubsImageEvent event) {
                System.out.println("Do what you want with image!");
            }
        });
        
        // Update with 
        new Timer(50, e -> {
            if (ass != null) {
                //From timer -> millis
                //Required -> nanos
                nanosPosition += TimeUnit.MILLISECONDS.toNanos(50L);
                controller.createFX(ass, nanosPosition);
            }
        }).start();
    }
    
}
```

You can play with the demo program by launching the Assj.java main class. A fake ASS is used in memory.

The demo with the use of 'Essai' font:
<img src="https://github.com/TW2/assj/blob/master/screenshots/Capture%20assj%20001.png" />
