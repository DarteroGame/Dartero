package com.example.dartero;

/**
 * A class to handle game updates in a separate thread
 */
public class GameUpdateRunnable implements Runnable {
    private boolean running = false;
    private Thread thread;
    private Game game;
    private final int targetFPS = 60;

    private boolean paused = false;


    /**
     * Constructor for the GameUpdateRunnable class
     * @param game The Game instance
     */
    public GameUpdateRunnable(Game game) {
        this.game = game;
    }

    /**
     * Start thread
     */
    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
    }

    /**
     * Join thread
     */
    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pause the game update thread
     */
    public void pause() {
        paused = true;
    }

    /**
     * Resume the game update thread
     */
    public void resume() {
        paused = false;
    }

    @Override
    public void run() {
        long targetInterval = 1000 / targetFPS;
        while(running) {
            while(paused) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            game.update();
            try {
                long elapsedTime = System.currentTimeMillis() % targetInterval;
                long sleepTime = targetInterval - elapsedTime;
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
