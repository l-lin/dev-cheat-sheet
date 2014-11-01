package game.onboarding;

import com.google.common.collect.ComparisonChain;
import lombok.Data;

@Data
public class Enemy implements Comparable<Enemy> {
    private String name;
    private int distance;
    private int round;
    public Enemy(String name, int distance, int round) {
        this.name = name;
        this.distance = distance;
        this.round = round;
    }

    @Override
    public int compareTo(Enemy that) {
        return ComparisonChain.start()
                .compare(this.distance, that.distance)
                .compare(this.name, that.name)
                .result();
    }
}
