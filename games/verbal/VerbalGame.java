package com.example.messagingstompwebsocket.games.verbal;

//import com.example.messagingstompwebsocket.brainbright.BrainBright;
import com.example.messagingstompwebsocket.games.Game;
import com.example.messagingstompwebsocket.utilities.FileManager;

public abstract class VerbalGame extends Game {

//    protected final List<String> countries, sortedDictionary, splitEasy, plants,
//            countryList, names, occupations, animals, simpleDict, simpleShort;
//    protected final String[] wordex;
	protected final Character[] vowels_gr = {'Α', 'Ε', 'Η', 'Ι', 'Ο', 'Υ', 'Ω'};
    protected final Character[] vowels_en = {'A', 'E',  'I', 'O', 'U'};
    protected final Character[] consonants_gr = {'Β', 'Γ', 'Δ', 'Ζ', 'Θ', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Π', 'Ρ', 'Σ', 'Τ', 'Φ', 'Χ', 'Ψ'};
    protected final Character[] consonants_en = {'B', 'G', 'D', 'Z', 'T', 'K', 'L', 'M', 'N', 'C', 'P', 'R', 'S', 'Τ', 'F', 'Χ', 'W', 'H', 'J', 'Q', 'V', 'Y'};
    protected final Character[] easyLetters_gr = {'Α', 'Γ', 'Κ', 'Λ', 'Μ', 'Π', 'Σ'};
    protected final Character[] easyLetters_en = {'A', 'G', 'K', 'L', 'M', 'P', 'S'};
    protected final Character[] hardLetters_gr = {'Ε', 'Β', 'Δ', 'Ο', 'Ν', 'Ρ', 'Τ', 'Χ'};
    protected final Character[] hardLetters_en = {'E', 'B', 'D', 'O', 'N', 'R', 'T', 'X'};
    protected final FileManager fileManager;

    protected VerbalGame() {
        super();
        //fileManager = BrainBright.FILES;
        fileManager = new FileManager();
        //fileManager = null;
    }
}
