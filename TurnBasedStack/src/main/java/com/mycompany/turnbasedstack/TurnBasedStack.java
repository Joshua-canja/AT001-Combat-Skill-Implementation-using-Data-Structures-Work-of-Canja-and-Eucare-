
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.turnbasedstack;

/**
 *
 * @author Student's Account
 */
import java.util.Random;
import java.util.Stack;
import java.util.Scanner;

public class TurnBasedStack {

    public static void main(String[] args) {

        Stack<Integer> lastHP = new Stack<>();
        Stack<Integer> currentDmg = new Stack<>();
        Stack<Integer> jinguBuffStack = new Stack<>();

        Scanner scan = new Scanner(System.in);

        Random random = new Random();

        int playerHP = 1000;
        int botHP = 500;
        int playerMaxDmg = 10;
        int playerMinDmg = 5;
        int botMaxDmg = 10;
        int botMinDmg = 5;

        int jinguMastery = 4;
        int stunCooldown = 0;

        int absDefCooldown = 0;
        int absDefTurns = 0;

        double[] dmgReduction = {1.0, 0.8};
        boolean absoluteDef = false;

        while (true) {

            if (botHP <= 0) {
                System.out.println("The Enemy has died");
                break;
            }
            if (playerHP <= 0) {
                System.out.println("Player has died!");
                break;
            }
            if (botHP >= botHP) {
                botHP = botHP;
            }

            // player's turn
            System.out.print("Player HP: " + playerHP);
            System.out.println("      Monster HP: " + botHP);
            System.out.println(" ");
            System.out.println("What would you like to do?");
            System.out.println("1. Attack");
            System.out.println("2. Stun");
            System.out.println("3. Absolute Defense");
            System.out.println("4. Skip");
            System.out.println(" ");
            System.out.print("Pick a choice: ");

            String in = scan.nextLine();

            if (in.equalsIgnoreCase("Exit")) {
                System.out.println("You exit the game");
                break;
            }

            if (in.equalsIgnoreCase("1")) {
                int playerDmg = playerMaxDmg - random.nextInt(playerMinDmg);
                boolean jinguActiveThisTurn = false;

                if (!jinguBuffStack.isEmpty()) {
                    playerDmg += playerDmg * 0.80;
                    jinguBuffStack.pop();
                    jinguActiveThisTurn = true;
                }
                if (jinguMastery > 0 && jinguBuffStack.isEmpty()) {
                    jinguMastery--;
                    if (jinguMastery == 0) {
                        for (int i = 0; i < 4; i++) {
                            jinguBuffStack.push(1);
                        }
                        System.out.println();
                        System.out.println("Jingu Mastery buff has been activated for the next 4 turns!");
                        jinguActiveThisTurn = true; // Buff will be applied starting next turn
                    }
                }

                System.out.println();
                System.out.println("You Dealt " + playerDmg + " damage to the Enemy");
                lastHP.push(botHP);
                botHP = botHP - playerDmg;
                if (botHP <= 0) {
                    botHP = 0;
                }
                System.out.println("The enemy has " + botHP + " HP reamaining.");
                System.out.println();

                if (jinguActiveThisTurn) {
                    System.out.println("Jingu Mastery buff! +80% bonus damage. (" + jinguBuffStack.size() + " turn(s) left)");
                    System.out.println(" ");
                }
                if (jinguBuffStack.isEmpty() && jinguActiveThisTurn) {
                    System.out.println("Jingu Mastery buff has worn off!");
                    System.out.println(" ");
                    jinguMastery = 4;
                }

            } else if (in.equalsIgnoreCase("2")) {
                if (stunCooldown == 0) {
                    System.out.println(" ");
                    System.out.println("The enemy has been stunned!");
                    System.out.println("Your turn to attack");
                    System.out.println(" ");
                    stunCooldown = 4;
                    continue;

                } else {
                    System.out.println(" ");
                    System.out.println("Stun is on cooldown for " + stunCooldown + " more turns.");
                    System.out.println(" ");
                    continue;
                }
            } else if (in.equals("3")) {
                if (absDefCooldown > 0 || absDefTurns > 0) {
                    System.out.println("Absolute Defense is on cooldown for " + absDefCooldown + " more turns or already active!");
                    System.out.println(" ");
                    continue;
                } else {
                    System.out.println("Absolute Defense Activated for 2 turns!");
                    absDefTurns = 2;
                    absDefCooldown = 3;
                }
            }
            //Enemy turn
            int botDmg = botMaxDmg - random.nextInt(botMinDmg);
            System.out.println("It is the monster's turn now!!");
            System.out.println(" ");
            System.out.println("The Monster Dealt " + botDmg + " damage to the Player");

            if (absDefTurns > 0) {
                double reduction = dmgReduction[1];
                int reducedDmg = (int) Math.round(botDmg * (1 - reduction));
                playerHP -= reducedDmg;
                absDefTurns--;
                System.out.println("Absolute Defense! Damage reduced by 80%: You took " + reducedDmg + " damage. (" + absDefTurns + " turn(s) left)");
            } else {
                playerHP -= botDmg;
            }
            if (playerHP < 0) {
                playerHP = 0;
            }
            System.out.println("The Player has " + playerHP + " HP remaining");
            System.out.println(" ");

            if (absDefCooldown > 0 && absDefTurns == 0) {
                absDefCooldown--;
            }

            // enemy passive
            int VampireFang = random.nextInt(4);
            if (VampireFang == 0) {
                System.out.println("Vampire Fang has been activated!");
                System.out.println("");
                botHP = (int) (botHP + (botDmg * 0.50));
                System.out.println("The Monster's Health restored to " + botHP);
                System.out.println(" ");
            }

            if (stunCooldown > 0) {
                stunCooldown--;

            }
        }
    }
}
