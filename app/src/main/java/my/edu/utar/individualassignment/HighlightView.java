package my.edu.utar.individualassignment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class HighlightView extends View {
    private static final int ANIMATION_DURATION = 250;
    public boolean isHighlighted;
    private boolean isHighlightedp;
    private Paint paint;
    private int alpha;
    private int level;
    private Rect highlightedRect;
    private int squareSize;

    public HighlightView(Context context) {
        super(context);
        isHighlighted = false;
        paint = new Paint();
        alpha = 0;
        this.level = level;
        squareSize = level + 1;
    }

    public void highlight() {
        isHighlighted = true;
        startAnimation(0, 255);
    }

    public void unhighlight() {
        isHighlighted = false;
        startAnimation(255, 0);
    }

    private void startAnimation(int startAlpha, int endAlpha) {
        ValueAnimator animator = ValueAnimator.ofInt(startAlpha, endAlpha);
        animator.setDuration(ANIMATION_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Draw unhighlighted square
        int unhighlightedSize = Math.min(width, height) - 10;
        int unhighlightedX = (width - unhighlightedSize) / 2;
        int unhighlightedY = (height - unhighlightedSize) / 2;
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawRect(unhighlightedX, unhighlightedY, unhighlightedX + unhighlightedSize, unhighlightedY + unhighlightedSize, paint);

        // Draw highlighted square
        int highlightedSize = squareSize * unhighlightedSize / squareSize;
        int highlightedX = (width - highlightedSize) / 2;
        int highlightedY = (height - highlightedSize) / 2;
        paint.setColor(Color.argb(alpha, 255, 255, 0));
        paint.setStyle(Paint.Style.FILL);
        highlightedRect = new Rect(highlightedX, highlightedY, highlightedX + highlightedSize, highlightedY + highlightedSize);
        canvas.drawRect(highlightedRect, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawRect(highlightedRect, paint);
    }
}
