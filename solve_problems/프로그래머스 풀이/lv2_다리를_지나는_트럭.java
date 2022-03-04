import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;
 
        ArrayList<Integer> ingTruck = new ArrayList<>();
        ArrayList<Integer> ingTruckTime = new ArrayList<>();
        
        int time = 0, index = 0, sumWeight = 0;
        int temp = 0;
        while(index < truck_weights.length) {
            time++;
            
            // add sec
            addSecond(ingTruckTime);

            // minus w
            sumWeight -= minusWeight(ingTruckTime, ingTruck, bridge_length);
            
            // plus w
            temp = truck_weights[index];
            if (sumWeight + temp <= weight ) {
                sumWeight += temp;
                ingTruckTime.add(0);
                ingTruck.add(temp);
                index++;
            }
        }
        while(ingTruck.size() != 0) {
            time++;
            addSecond(ingTruckTime);
            sumWeight -= minusWeight(ingTruckTime, ingTruck, bridge_length); 
        } 
        answer = time;
        return answer;
    }
    public void addSecond(ArrayList<Integer> ingTruckTime) {
        for (int i = 0; i < ingTruckTime.size(); i++)
            ingTruckTime.set(i, ingTruckTime.get(i) + 1);
    }
    public int minusWeight(ArrayList<Integer> ingTruckTime, ArrayList<Integer> ingTruck, int bridge_length) {
        if ( ingTruckTime.size() != 0 && ingTruckTime.get(0) == bridge_length ) {
            ingTruckTime.remove(0);
            return ingTruck.remove(0);
        }   
        return 0;
    }
}