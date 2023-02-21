package org.example;

public class Jugador {
    String username;
    int score;
    int amountAtStake;
    int wildcard75;
    int wildcardIA;
    int wildcardDecline;
    int wildcardMultiply;
    boolean wildcardCancelEffect;
    int lives;
    boolean leaveGame;

    public Jugador() {
        this.score = 250;
        this.amountAtStake = 0;
        this.wildcard75 = 3;
        this.wildcardDecline = 3;
        this.wildcardIA = 3;
        this.wildcardMultiply = 3;
        this.wildcardCancelEffect = true;
        this.lives = 10;
        this.leaveGame = false;
    }

    public Jugador(String username, int score, int amountAtStake, int wildcard75, int wildcardIA, int wildcardDecline, int wildcardMultiply, boolean wildcardCancelEffect, int lives, boolean leaveGame) {
        this.username = username;
        this.score = score;
        this.amountAtStake = amountAtStake;
        this.wildcard75 = wildcard75;
        this.wildcardIA = wildcardIA;
        this.wildcardDecline = wildcardDecline;
        this.wildcardMultiply = wildcardMultiply;
        this.wildcardCancelEffect = wildcardCancelEffect;
        this.lives = lives;
        this.leaveGame = leaveGame;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAmountAtStake() {
        return amountAtStake;
    }

    public void setAmountAtStake(int amountAtStake) {
        this.amountAtStake = amountAtStake;
    }

    public int getWildcard75() {
        return wildcard75;
    }

    public void setWildcard75(int wildcard75) {
        this.wildcard75 = wildcard75;
    }

    public int getWildcardIA() {
        return wildcardIA;
    }

    public void setWildcardIA(int wildcardIA) {
        this.wildcardIA = wildcardIA;
    }

    public int getWildcardDecline() {
        return wildcardDecline;
    }

    public void setWildcardDecline(int wildcardDecline) {
        this.wildcardDecline = wildcardDecline;
    }

    public int getWildcardMultiply() {
        return wildcardMultiply;
    }

    public void setWildcardMultiply(int wildcardMultiply) {
        this.wildcardMultiply = wildcardMultiply;
    }

    public boolean isWildcardCancelEffect() {
        return wildcardCancelEffect;
    }

    public void setWildcardCancelEffect(boolean wildcardCancelEffect) {
        this.wildcardCancelEffect = wildcardCancelEffect;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isLeaveGame() {
        return leaveGame;
    }

    public void setLeaveGame(boolean leaveGame) {
        this.leaveGame = leaveGame;
    }

    @Override
    public String toString() {
        return "\t● Score: " + score +
                "\n\t● AmountAtStake: " + amountAtStake +
                "\n\t● Wildcard75: " + wildcard75 +
                "\n\t● WildcardIA: " + wildcardIA +
                "\n\t● WildcardDecline: " + wildcardDecline +
                "\n\t● WildcardMultiply: " + wildcardMultiply +
                "\n\t● WildcardCancelEffect: " + wildcardCancelEffect +
                "\n\t● Lives: " + lives;
    }
}
