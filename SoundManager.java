import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SoundManager {
    private static final int SAMPLE_RATE = 44100;

    public void playJump() {
        playTone(900, 120, 0.18f);
    }

    public void playScore() {
        playTone(1320, 80, 0.12f);
    }

    public void playCollision() {
        playTone(220, 220, 0.28f);
    }

    private void playTone(int frequency, int durationMs, float amplitude) {
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
                byte[] data = createTone(frequency, durationMs, amplitude);
                try (Clip clip = AudioSystem.getClip()) {
                    clip.open(format, data, 0, data.length);
                    clip.start();
                    Thread.sleep(durationMs + 20);
                }
            } catch (LineUnavailableException | InterruptedException | IOException ignored) {
                // Fallback: no audio available.
            }
        }).start();
    }

    private byte[] createTone(int frequency, int durationMs, float amplitude) throws IOException {
        int sampleCount = SAMPLE_RATE * durationMs / 1000;
        byte[] data = new byte[sampleCount * 2];

        for (int i = 0; i < sampleCount; i++) {
            double angle = 2.0 * Math.PI * i * frequency / SAMPLE_RATE;
            short sample = (short) (Math.sin(angle) * amplitude * Short.MAX_VALUE);
            data[i * 2] = (byte) (sample & 0xFF);
            data[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
        }

        return data;
    }
}
