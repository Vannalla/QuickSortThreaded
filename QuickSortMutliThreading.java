import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Scanner;
  
public class QuickSortMutliThreading extends RecursiveTask<Integer> {

    int start;
    int end;
    int[] arr;
  
    public static void main(String args[]){
        System.out.println("Testing threaded quicksort.");
        System.out.println("Please enter size of the array you would like to sort, Note that the code wont print arrays bigger than 100, but it does sort! ");
        Scanner scan = new Scanner(System.in);
        int size = scan.nextInt();
        Random rand = new Random();
        int[] arr = new int[size];
        for(int i = 0; i<arr.length; i++){
            arr[i]=rand.nextInt(100);
        }

        int n = arr.length;

        if(arr.length< 100){
            System.out.print("Unsorted array: ");
            for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
            System.out.print("\n");
        }
        //ForkJoinPool pool = ForkJoinPool.commonPool();
        ForkJoinPool pool = new ForkJoinPool();

        pool.invoke(new QuickSortMutliThreading(0, n - 1, arr));

        if(arr.length< 100){
            System.out.print("Sorted array: ");
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        }
        System.out.println("All Done!");
        scan.close();
    }
    private int partion(int start, int end, int[] arr){
        int i = start, j = end;
        int pivot = new Random().nextInt(j - i) + i; // Decide random pivot location
        int t = arr[j]; // Swap the pivot with end element of array;
        arr[j] = arr[pivot];
        arr[pivot] = t;
        j--;
        while (i <= j) {         // Start partioning
            if (arr[i] <= arr[end]) {
                i++;
                continue;
            }
            if (arr[j] >= arr[end]) {
                j--;
                continue;
            }
            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            j--;
            i++;
        }
        t = arr[j + 1];// Swap pivot to its correct position
        arr[j + 1] = arr[end];
        arr[end] = t;
        return j + 1;
    }
  
    @Override
    protected Integer compute(){
        if (start >= end) return null;
        int p = partion(start, end, arr);
        QuickSortMutliThreading left= new QuickSortMutliThreading(start,p - 1,arr);
        QuickSortMutliThreading right = new QuickSortMutliThreading(p + 1,end,arr);
        left.fork();
        right.compute();
        left.join();
        return null;
    }

    public QuickSortMutliThreading(int start,int end,int[] arr){
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

}