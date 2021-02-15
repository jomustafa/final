package com.example.messagingstompwebsocket.games.pattern.reaction;;


    public abstract class ReactionPattern {


        protected int size;
        protected int[][] pattern;



        protected abstract void randomizePattern(int numPattern);


        public int getSize() {
            return size;
        }

        public int[][] getPattern() {
            return pattern;
        }
    }

