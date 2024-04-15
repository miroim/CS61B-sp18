public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque d = new Deque.ArrayDeque();
        for (int i = 0; i < word.length(); i += 1) {
            char c = word.charAt(i);
            d.addLast(c);
        }
        return d;
    };

    private boolean isPalindrome(Deque d) {
        if (d.size() <= 1) {
            return true;
        }
        char first = (char) d.removeFirst();
        char last = (char) d.removeLast();
        return first == last && isPalindrome(d);
    }

    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);
        return isPalindrome(d);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);
        while (d.size() > 1) {
            char first = (char) d.removeFirst();
            char last = (char) d.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
        }
        return true;
    }
}
