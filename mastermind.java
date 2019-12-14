import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;


public class mastermind
{
    public class player
    {
        public String name;
        public player(String name_in)
        {
            name = name_in;
        }
    }

    public class feedback
    {
        public int white_hits, black_hits;
        public feedback(int wh_in, int bh_in)
        {
            white_hits = wh_in;
            black_hits = bh_in;
        }
        public boolean equals(feedback other)
        {
            return ((white_hits == other.white_hits) && (black_hits == other.black_hits));
        }
    }

    public feedback compare_codes(String code1, String code2)
    {

    }

    public class master extends player
    {
        private String code;   
        public master(String name_in)
        {
            super(name_in);
            Random r = new Random();
            code = "";
            for (int i = 0 ; i < 4; i++)
            {
                code += (r.nextInt(6)+1);
            }
        }
        public feedback evaluate_guess(String guess)
        {
            return compare_codes(guess, code);
        }
    }

    public class mind extends player
    {
        public Set<String> guess_set;
        public mind(String name_in) 
        {
            super(name_in);
            guess_set = generate_combinations("",4);
        }

        public String make_guess()
        {
            return guess_set.iterator().next();
        }
        public void learn(feedback f)
        {
            guess_set = new HashSet<String>();
            guess_set = guess_set.stream().filter(x -> compare_codes(x,guess_set.iterator().next()) == f).collect(Collectors.toSet());
        }
    }

    public static Set<String> generate_combinations(String input, int depth)
    {
        var set = new HashSet<String>();
        if (depth != input.length())
        {
            for (int i = 1 ; i <= 6; i++)
            {
                set.addAll(generate_combinations(input+i,depth));
            }
        }
        else
        {
            set.add(input);
        }
        return set;
    }



    public static void main(String[] args)
    {
        master ma = new master("Archie");
        mind mi = new mind("George");
        mi.learn(ma.evaluate_guess(mi.make_guess()));
        System.out.println(mi.guess_set.size());

        // var c = generate_combinations("", 6);
        // System.out.println(c);
        // System.out.println(c.size());
        // System.out.println(c.iterator().next());

    }

}