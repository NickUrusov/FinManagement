package edu.innotech.model;

public enum TypeTransaction {
    CREDITING,
    DEBITING;

    private static TypeTransaction[] $values(){
        return new TypeTransaction[]{CREDITING, DEBITING};
    }
}
