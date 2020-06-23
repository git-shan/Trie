import java.util.TreeMap;

public class Trie {
    private class Node {
        public TreeMap<Character, Node> next;
        public boolean isWord;

        public Node(boolean isWord){
            this.isWord = isWord;
            next = new TreeMap<>();
        }
        public Node(){
            this(false);
        }
        @Override
        public String toString(){
           return next.toString();
        }
    }
    private Node root;
    private int size;
    private Array<StringBuilder> pTrie;
    public Trie(){
        root = new Node();
        size =0;
        pTrie = new Array<>();
        pTrie.addLast(new StringBuilder());
    }

    public int getSize(){
        return size;
    }

    public void add(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            Character c = Character.toLowerCase(word.charAt(i)); // ignore capital, store in low case
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if (!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }
    public void addR(String word){
            addR(root, word, 0);
    }

    public void addR(Node node, String word,int chp){
        Character c = Character.toLowerCase(word.charAt(chp)); // ignore capital, store in low case
        if (node.next.get(c) == null) {
            node.next.put(c, new Node());
            if (!node.next.get(c).isWord && chp == word.length() - 1) {
                node.next.get(c).isWord = true;
                size++;
            }
        }
        if(chp<word.length()-1) addR(node.next.get(c), word, chp+1 );
    }
    public boolean match(String word){
       return match(root, word, 0);
    }

    private boolean match(Node node, String word,int chp){
        if(chp==word.length())
            return (node.isWord);

        Character c = Character.toLowerCase(word.charAt(chp));
        if(c!='.'){
            if(node.next.get(c)==null)
                return false;
            return match(node.next.get(c),word,chp+1);
        }
        else{
//            skip character check
            for(Character k:node.next.keySet()){
                return match(node.next.get(k),word,chp+1);
            }
//            no match in all of keys for "."
            return false;
        }
    }
    public boolean contains(String word){
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            Character c = Character.toLowerCase(word.charAt(i)); // ignore capital, store in low case
            if (cur.next.get(c) == null) {
                return false;
            } else
                cur = cur.next.get(c);
        }
        return (cur.isWord);
    }
    public boolean isPrefix(String prefix){
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            Character c = Character.toLowerCase(prefix.charAt(i)); // ignore capital, store in low case
            if (cur.next.get(c) == null) {
                return false;
            } else
                cur = cur.next.get(c);
        }
        return true;
    }
    @Override
    public String toString() {
        return root.toString();
    }

    public void printTrie(){
//       pTrie.get(0).append("Trie: readable");
       Character c;
       StringBuilder s;
       printTrie(root, 0);
       for(int i=0; i<pTrie.getSize();i++){
           pTrie.get(i).insert(0,"LVL "+i+" :");
           System.out.println(pTrie.get(i).toString());
       }
    }

    private void printTrie(Node node,int depth){
        for(int i=pTrie.getSize();i<=depth;i++)
            pTrie.addLast(new StringBuilder());

        StringBuilder pd = pTrie.get(depth);
        if(node.next.isEmpty()){
            pd.append("{| |}\t");
            return;
        }
        Object [] keys = node.next.keySet().toArray();
        pd.append("{");
        for(Object k: keys){
           pd.append("|");
           pd.append(k.toString());
           printTrie(node.next.get((Character)k),depth+1);
           pd.append("|");
        }
        pd.append("}\t");
    }

    public static void main(String[] args) {
        // write your code here
        String [] arrWord = {"see","sea","tom", "tom", "henry", "Gang", "Shan","Jing","Jean","Sam","Ada", "python"};
        Trie trie = new Trie();
        for(int i=0;i<arrWord.length;i++){
//            trie.add(arrWord[i]);
            trie.addR(arrWord[i]);
        }
        System.out.println(trie);
        trie.printTrie();
        System.out.println(trie.contains("see"));
        System.out.println(trie.contains("se"));
        System.out.println(trie.isPrefix("Gan"));
        System.out.println(trie.isPrefix("Gan."));
        System.out.println(trie.match("Gan."));

    }

}
