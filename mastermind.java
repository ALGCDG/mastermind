import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

class player
{
    public String name;
    public player(String name_in)
    {
        name = name_in;
    }
}

class feedback
{
    public int white_hits, black_hits;
    public feedback(int wh_in, int bh_in)
    {
        white_hits = wh_in;
        black_hits = bh_in;
    }
    public boolean equals(feedback other)
    {
        return ((this.white_hits == other.white_hits) && (this.black_hits == other.black_hits));
    }
    public String toString()
    {
        return white_hits + ", " + black_hits;
    }
    public static feedback compare_codes(String code1, String code2)
    {
        int black_hits = 0;
        int white_hits = 0;
        for (int i = 0; i < code1.length() ; i++)
        {
            if (code1.charAt(i) == code2.charAt(i))
            {
                black_hits++;
            }
            else 
            {
                for (int j = 0; j < code2.length() ; j++)
                {
                    if (code1.charAt(i) == code2.charAt(j))
                    {
                        white_hits++;
                    }
                }
            }
        }
        return new feedback(white_hits, black_hits);
    }
}



class master extends player
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
        return feedback.compare_codes(guess, code);
    }
}

class mind extends player
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
        // guess_set = new HashSet<String>();
        // guess_set = guess_set.stream().filter(x -> compare_codes(x,guess_set.iterator().next()) == f).collect(Collectors.toSet());
        String last_guess = guess_set.iterator().next();
        guess_set.removeIf(x -> !feedback.compare_codes(x, last_guess).equals(f));
        // System.out.println("code " + last_guess + " produced feedback: " + f);
        // for (var x : guess_set)
        // {
        //     var n = feedback.compare_codes(x, last_guess);
        //     System.out.println(n + ", match? " + (f.equals(n)));
            
        // }
    }

    private static Set<String> generate_combinations(String input, int depth)
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
}

public class mastermind
{
    public static void main(String[] args)
    {
        master ma = new master("Archie");
        mind mi = new mind("George");
        // System.out.println(mi.make_guess());
        // System.out.println(ma.evaluate_guess(mi.make_guess()));
        feedback ideal = new feedback(0,4);
        // feedback result = new feedback();
        for(;;)
        {
            mi.learn(ma.evaluate_guess(mi.make_guess()));
            System.out.println(mi.guess_set.size());
        }

        // var c = generate_combinations("", 6);
        // System.out.println(c);
        // System.out.println(c.size());
        // System.out.println(c.iterator().next());

        // var s = new HashSet<String>();
        // for (int i = 0; i < 11;i++)
        // {
        //     s.add("" + i);
        // }
        // s.removeIf(i -> (Integer.parseInt(i) % 2) == 0);
        // System.out.println(s);

    }
}