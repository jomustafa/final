package com.example.messagingstompwebsocket.games.visual.spotdifferences;


import com.example.messagingstompwebsocket.games.visual.VisualGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Odysseas on 30/10/2018.
 */
public class Spotdifferences  {

	  private enum ERROR {error}
	    private enum level1pair {aladin,idk,lol,sponge,tomAndJerry,weirdCreatures,winnieThePooh,disney,bugsBunny,clown_3,bird_3,pig_3,girl_3,doggirl_3,spongie_3,boy_3,look_3}
	    private enum level2pair {peter_7,bird_5,scoobydoo,mermaid,winnie,simpsons,blackAndWhite,bird,fix,doraemon,bear,dog_5,vutt_5,batman_5,apple_5,heros_5,com_5,out_5}
	    private enum level3pair{sketch_7,flowers_7,bob,hercules,mage,mouse,robot,spider,spongeFbob,bunny,winniew_7,black_7,sleep_7,house_7,man_7}
	    private enum level4pair{cinderella_7,meg_7,hercules_7, pond_7,scared_7,bugsking_7,king_7,bugbunny,berries,beach,girl,looney}
	    private enum level5pair{janie_10,birthday_10,poohw_10,alice_10,xmas_10,police_10,angry_10,disney10,luffy,luffy_2,pokemon,pokemon_2,sadFox,cartoon_10,cats_10}

	    private static final int WIDTH = 25;
	    private static final int HEIGHT = 25;


	    private Pair pair;

	    protected int goal;
	    protected int timeAllowed;

	    //i take the text from
	    public Spotdifferences(int level) {

	        if(level==1) {
	            String pair_name = randomPair(level);
	            pair = new Pair(pair_name);
	            goal = 7;
	            timeAllowed = 300;
	        }else if(level==2){
	            String pair_name = randomPair(level);
	            pair = new Pair(pair_name);
	            goal = 5;
	            timeAllowed = 300;
	        }else if(level==5){
	            String pair_name = randomPair(level);
	            pair = new Pair(pair_name);
	            goal = 10 ;
	            timeAllowed = 300;
	        }else {
	            String pair_name = randomPair(level);
	            pair = new Pair(pair_name);
	            goal = 7;
	            if(level==3)
	                timeAllowed = 420;
	            else if(level==4)
	                timeAllowed = 300;

	        }
	    }
	    public int getTimeAllowed() {
	        return timeAllowed;
	    }
	    public String getPath(){

	        return pair.getPath();
	    }
	    public int getDifferencesNo() {
	        return pair.getDifferencesNo();
	    }

	    private String randomPair(int level) {
	        Random r = new Random();
	        switch (level) {

	            case 1:
	                switch (r.nextInt(level1pair.values().length)+1){

	                    case 1: return level1pair.aladin.toString();
	                    case 2: return level1pair.bugsBunny.toString();
	                    case 3: return level1pair.disney.toString();
	                    case 4: return level1pair.idk.toString();
	                    case 5: return level1pair.lol.toString();
	                    case 6: return level1pair.tomAndJerry.toString();
	                    case 7: return level1pair.look_3.toString();
	                    case 8: return level1pair.sponge.toString();
	                    case 9: return level1pair.weirdCreatures.toString();
	                    case 10: return level1pair.winnieThePooh.toString();
	                    case 11: return level1pair.clown_3.toString();
	                    case 12: return level1pair.bird_3.toString();
	                    case 13: return level1pair.pig_3.toString();
	                    case 14: return level1pair.girl_3.toString();
	                    case 15: return level1pair.doggirl_3.toString();
	                    case 16: return level1pair.spongie_3.toString();
	                    case 17: return level1pair.boy_3.toString();
	                }
	            case 2:
	                switch (r.nextInt(level2pair.values().length)+1){
	                    case 1: return level2pair.scoobydoo.toString();
	                    case 2: return level2pair.mermaid.toString();
	                    case 3: return level2pair.winnie.toString();
	                    case 4: return level2pair.bird_5.toString();
	                    case 5: return level2pair.simpsons.toString();
	                    case 6: return level2pair.blackAndWhite.toString();
	                    case 7: return level2pair.bird.toString();
	                    case 8: return level2pair.fix.toString();
	                    case 9: return level2pair.doraemon.toString();
	                    case 10: return level2pair.bear.toString();
	                    case 11: return level2pair.dog_5.toString();
	                    case 12: return level2pair.batman_5.toString();
	                    case 13: return level2pair.heros_5.toString();
	                    case 14: return level2pair.com_5.toString();
	                    case 15: return level2pair.out_5.toString();
	                    case 16: return level2pair.apple_5.toString();
	                    case 17: return level2pair.vutt_5.toString();
	                    case 18: return level2pair.peter_7.toString();
	                }
	            case 3:
	                switch (r.nextInt(level3pair.values().length) + 1) {
	                    case 1: return level3pair.bob.toString();
	                    case 2: return level3pair.hercules.toString();
	                    case 3: return level3pair.mage.toString();
	                    case 4: return level3pair.mouse.toString();
	                    case 5: return level3pair.robot.toString();
	                    case 6: return level3pair.spider.toString();
	                    case 7: return level3pair.spongeFbob.toString();
	                    case 8: return level3pair.bunny.toString();
	                    case 9: return level3pair.winniew_7.toString();
	                    case 10: return level3pair.black_7.toString();
	                    case 11: return level3pair.sleep_7.toString();
	                    case 12: return level3pair.house_7.toString();
	                    case 13: return level3pair.man_7.toString();
	                    case 14: return level3pair.flowers_7.toString();
	                    case 15: return level3pair.sketch_7.toString();
	                    default:
	                }

	            case 4:
	                switch (r.nextInt(level4pair.values().length) + 1) {
	                    case 1: return level4pair.berries.toString();
	                    case 2: return level4pair.bugbunny.toString();
	                    case 3: return level4pair.beach.toString();
	                    case 4: return level4pair.hercules_7.toString();
	                    case 5: return level4pair.girl.toString();
	                    case 6: return level4pair.looney.toString();
	                    case 7: return level4pair.meg_7.toString();
	                    case 8: return level4pair.pond_7.toString();
	                    case 9: return level4pair.cinderella_7.toString();
	                    case 10: return level4pair.scared_7.toString();
	                    case 11: return level4pair.king_7.toString();
	                    case 12: return level4pair.bugsking_7.toString();
	                    default:
	                }
	            case 5:
	                switch (r.nextInt(level5pair.values().length) + 1) {
	                    case 1: return level5pair.disney10.toString();
	                    case 2: return level5pair.luffy.toString();
	                    case 3: return level5pair.luffy_2.toString();
	                    case 4: return level5pair.pokemon.toString();
	                    case 5: return level5pair.pokemon_2.toString();
	                    case 6: return level5pair.birthday_10.toString();
	                    case 7: return level5pair.sadFox.toString();
	                    case 8: return level5pair.cartoon_10.toString();
	                    case 9: return level5pair.janie_10.toString();
	                    case 10: return level5pair.cats_10.toString();
	                    case 11: return level5pair.angry_10.toString();
	                    case 12: return level5pair.police_10.toString();
	                    case 13: return level5pair.xmas_10.toString();
	                    case 14: return level5pair.alice_10.toString();
	                    case 15: return level5pair.poohw_10.toString();
	                    default:
	                }
	            default:
	                return ERROR.error.toString();
	        }
	    }

	    // check if the selected coordinates are corresponding to a difference
	    // but with the use of a biases , WIDTH and HEIGHT = 200  pixels
	    public boolean checkSelectedCoords(double x, double y){

	        ArrayList<Difference> temp = pair.getDifferences();

	        for(int i = 0;i < temp.size(); i++){
	            if(Math.abs(x - temp.get(i).getX_cord()) <= WIDTH && Math.abs(y - temp.get(i).getY_cord())<= HEIGHT){
	                return true;
	            }
	        }
	        return false;
	    }
	    public ArrayList<Difference> getDifferences() {
	        return pair.getDifferences();
	    }






}
