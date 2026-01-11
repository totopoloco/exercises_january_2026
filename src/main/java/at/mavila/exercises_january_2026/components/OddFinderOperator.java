package at.mavila.exercises_january_2026.components;

import java.util.function.IntBinaryOperator;

import org.springframework.stereotype.Component;

@Component
public class OddFinderOperator implements IntBinaryOperator {

    @Override
    public int applyAsInt(int left, int right) {
        return left ^ right;
    }

}
