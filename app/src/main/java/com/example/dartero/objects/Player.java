package com.example.dartero.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.dartero.GameLoop;
import com.example.dartero.panel.HealthBar;
import com.example.dartero.panel.Joystick;
import com.example.dartero.R;

public class Player extends ObjectsWithHealth{

    public static final double SPEED_PIXEL_PER_SECOND = 1000;
    public static final int MAX_HEALTH_POINTS = 5;
    private static final double MAX_SPEED = SPEED_PIXEL_PER_SECOND / 100;

    public static final double radius = 70;
    private Paint paint;
    private PlayerState playerState;

    private final Joystick joystick;
    private HealthBar healthBar;

    public Player(Context context, double positionX, double positionY, Joystick joystick) {
        super(positionX, positionY, radius, MAX_HEALTH_POINTS);
        this.joystick = joystick;
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.player));
        this.playerState = new PlayerState(this);
        this.healthBar = new HealthBar(context, this);
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float) positionX, (float) positionY, (float) radius, paint);
        healthBar.draw(canvas);
    }

    public void update() {
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        //Update position to be within frame
        positionX = positionX + velocityX;
        if(positionX > maxX) {
            positionX = maxX;
        } else if (positionX < 0) {
            positionX = 0;
        }
        positionY = positionY + velocityY;
        if(positionY > maxY) {
            positionY = maxY;
        } else if (positionY < 0) {
            positionY = 0;
        }

        playerState.update();
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

}
