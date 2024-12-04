package org.keefeteam.atlantis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Queue;

@Getter
@Setter
@AllArgsConstructor
public class Noise implements Entity{
    private double intensity;

    private Queue<Double> intensityCurve;

    private double time = 0;

    @Override
    public void update(GameState gameState, List<InputEvent> events) {
        time += gameState.getDelta();

        if (time > 0.25) {
            if (intensityCurve.isEmpty()) {
                intensity = 0;
            }
            else {
                intensity = intensityCurve.remove();
                time -= 0.25;
            }
        }
    }
}
