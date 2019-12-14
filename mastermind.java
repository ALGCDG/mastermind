import java.util.*

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

    public class code
    {
        public String code;
        public bool increment()
        {
            code.foreach(++);
        }
        public code()
        {
            code = "1111";
        }
        public code(String in_code)
        {
            code = in_code;
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
    }

    public feedback compare_codes(String code1, String code2)
    {

    }

    public class master extends player
    {
        private code code;   
        public master(String name_in) : Base(name_in)
        {

        }
        public feedback evaluate_guess(String guess)
        {
            return compare_codes(guess, code);
        }
    }

    public class mind extends player
    {
        private Set<String> guess_set;
        public mind(String name_in) : Base(name_in)
        {
            code initial = new code();
            while (initial.increment())
            {
                guess_set.add(initial);
            }
        } 
    }

    public static void main(String args)
    {}
}