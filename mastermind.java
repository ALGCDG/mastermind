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
        var difference1 = new HashMap<Character, Integer>();
        var difference2 = new HashMap<Character, Integer>();
        for (int i = 0; i < code1.length() ; i++)
        {
            if (code1.charAt(i) == code2.charAt(i))
            {
                black_hits++;
            }
            else 
            {
                char k1 = code1.charAt(i);
                char k2 = code2.charAt(i);
                int v1 = difference1.containsKey(k1) ? difference1.get(k1) + 1 : 1;
                int v2 = difference2.containsKey(k2) ? difference2.get(k2) + 1 : 1;
                difference1.put(k1, v1);
                difference2.put(k2, v2);
            }
        }
        for (var k : difference1.keySet())
        {
            var w = difference2.containsKey(k) ? Math.min(difference1.get(k), difference2.get(k)) : 0 ;
            white_hits += w;
        }
        return new feedback(white_hits, black_hits);
    }
}



class master extends player
{
    private String code;   
    public master(String name_in, int code_length, int number_of_symbols)
    {
        super(name_in);
        Random r = new Random();
        code = "";
        for (int i = 0 ; i < code_length; i++)
        {
            code += Integer.toHexString(r.nextInt(number_of_symbols)+1);
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
    public mind(String name_in, int code_length, int number_of_symbols) 
    {
        super(name_in);
        guess_set = generate_combinations("", code_length, number_of_symbols);
    }

    public String make_guess()
    {
        return guess_set.iterator().next();
    }
    public void learn(feedback f)
    {
        String last_guess = guess_set.iterator().next();
        guess_set.removeIf(x -> !feedback.compare_codes(x, last_guess).equals(f));
    }

    private static Set<String> generate_combinations(String input, int depth, int no_symbols)
    {
        var set = new HashSet<String>();
        if (depth != input.length())
        {
            for (int i = 1 ; i <= no_symbols; i++)
            {
                set.addAll(generate_combinations(input+ Integer.toHexString(i),depth,no_symbols));
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
        int code_length, no_symbols;
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter the code length and number of symbols: ");
        code_length = s.nextInt();
        no_symbols = s.nextInt();

        master ma = new master("Archie", code_length, no_symbols);
        mind mi = new mind("George", code_length, no_symbols);
        feedback ideal = new feedback(0,code_length);
        feedback report;
        do
        {
            String guess = mi.make_guess();
            report = ma.evaluate_guess(guess);
            System.out.println("Mind " + mi.name + " guessed " + guess + ", Master " + ma.name + " responds with the feedback " + report);
            mi.learn(report);
        }
        while (!report.equals(ideal));
    }
}