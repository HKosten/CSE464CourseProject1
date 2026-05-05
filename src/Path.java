public class Path {
    String[] p;
    int length;
    int capacity;

    public Path() {
        p = new String[16];
        capacity = 16;
    }

    public void addNodeAtIndex(String s, int i) {
        if (length == capacity) {
            doubleCapacity();
        }
        p[i] = s;
        length++;
    }

    public void doubleCapacity() {
        String[] t = new String[capacity * 2];

        if (capacity >= 0) System.arraycopy(p, 0, t, 0, capacity);

        p = t;
        capacity = capacity * 2;
    }

    public void printPath(){
        System.out.print("visiting Path{nodes=[");
        for(int i = 0; i < this.length; i++){
            System.out.print("Node{");
            System.out.print(this.p[i]);
            System.out.print("}");
            if(i != this.length-1){
                System.out.print(", ");
            }
        }
        System.out.print("]} ");
    }
}
