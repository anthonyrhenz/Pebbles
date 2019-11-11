public class Main {
    public static boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }
    public static void main(String[] args)
    {
        System.out.println("Hello World!");

//        for (int i=1; i<10; i++){
//            System.out.println("Count is: " + i);
//        }

        int []numArray = {1,2,3,4,4,5,6,6,6,7};
        for (int i:numArray) {
            System.out.printf("List is %h%h",i,"meme end\n");
        }
        int i = 0;
        while(i != 10){
            System.out.println(i);
            i++;
        }

        int j=0;
        while(true){
            System.out.println(j+" | "+ isPrime(j));
            j++;
            if (j>1001){
                break;
            }
        }


    }
}