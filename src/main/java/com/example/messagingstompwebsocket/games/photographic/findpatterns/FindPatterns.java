package com.example.messagingstompwebsocket.games.photographic.findpatterns;

public class FindPatterns {

    Pattern [] patterns;

    public FindPatterns(int level) {
        patterns = createPattern(level);
    }

    private Pattern [] createPattern(int level)
    {
        switch(level)
        {
            case 1: return new Pattern [] {
                    new Pattern(3,3),
                    new Pattern(3,3),
                    new Pattern(3,4),
                    new Pattern(3,4),
                    new Pattern(3,4),
                    new Pattern(3,4),
                    new Pattern(4,5),
                    new Pattern(4,5),
                    new Pattern(4,6),
                    new Pattern(4,6)};
            case 2: return new Pattern [] {
                    new Pattern(3,3),
                    new Pattern(3,3),
                    new Pattern(3,4),
                    new Pattern(3,4),
                    new Pattern(4,5),
                    new Pattern(4,5),
                    new Pattern(4,6),
                    new Pattern(4,6),
                    new Pattern(4,6),
                    new Pattern(4,6)};
            case 3: return new Pattern [] {
                    new Pattern(3,3),
                    new Pattern(3,3),
                    new Pattern(4,4),
                    new Pattern(4,4),
                    new Pattern(4,5),
                    new Pattern(4,5),
                    new Pattern(4,6),
                    new Pattern(4,6),
                    new Pattern(5,7),
                    new Pattern(5,7)};
            case 4: return new Pattern [] {
                    new Pattern(4,3),
                    new Pattern(4,3),
                    new Pattern(4,4),
                    new Pattern(4,4),
                    new Pattern(4,5),
                    new Pattern(4,5),
                    new Pattern(4,6),
                    new Pattern(5,6),
                    new Pattern(5,7),
                    new Pattern(5,7)};
            case 5: return new Pattern [] {
                    new Pattern(4,4),
                    new Pattern(4,4),
                    new Pattern(4,5),
                    new Pattern(4,5),
                    new Pattern(4,6),
                    new Pattern(5,6),
                    new Pattern(4,7),
                    new Pattern(5,7),
                    new Pattern(5,8),
                    new Pattern(5,8)};
        }
        return null;
    }

    public Pattern[] getPatterns() {
        return patterns;
    }
}
