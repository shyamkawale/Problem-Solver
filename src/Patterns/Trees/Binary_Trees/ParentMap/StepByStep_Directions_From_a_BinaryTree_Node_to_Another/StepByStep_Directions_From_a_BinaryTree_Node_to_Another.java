package Patterns.Trees.Binary_Trees.ParentMap.StepByStep_Directions_From_a_BinaryTree_Node_to_Another; 
 
import Helpers.DataConvertor;
import Helpers.ProblemSolver;
import Helpers.DataStructure.Trees.TreeNode;
import Helpers.DataStructure.Trees.TreeWrapper; 
 
public class StepByStep_Directions_From_a_BinaryTree_Node_to_Another extends ProblemSolver { 
 
    public static void main(String[] args) { 
        new StepByStep_Directions_From_a_BinaryTree_Node_to_Another().readInput(); 
    } 
 
    @Override 
    public void processParameters(String[] args) { 
        TreeNode root = TreeWrapper.stringToTreeNode(args[0]);
        int startValue = DataConvertor.toInt(args[1]);
        int destValue = DataConvertor.toInt(args[2]);

        // TreeWrapper.prettyPrintTree(root);
 
        String res = getDirections(root, startValue, destValue); 
        System.out.println(res); 
    } 
 
    boolean startFound = false;
    boolean destFound = false;
    StringBuilder finalPath = new StringBuilder();
    StringBuilder path = new StringBuilder();
    public String getDirections(TreeNode root, int startValue, int destValue) {
        if(root == null) return "";
        preOrder(root, startValue, destValue);

        return startFound ? finalPath.toString() : finalPath.reverse().toString();
    }

    public void preOrder(TreeNode root, int start, int dest){
        if(root == null){
            return;
        }

        if(root.val == start){
            startFound = true;
        }
        else if(root.val == dest){
            destFound = true;
        }

        if(startFound == true && destFound == true){
            finalPath = path;
            return;
        }

        if(startFound || destFound){
            path.append("L");
        }
        preOrder(root.left, start, dest);
        if(startFound == true && destFound == true){
            finalPath = path;
            return;
        }
        if((startFound || destFound) && path.charAt(path.length()-1) == 'L'){
            path.deleteCharAt(path.length()-1);
        }

        if(startFound || destFound){
            path.append("R");
        }
        preOrder(root.right, start, dest);
        if(startFound == true && destFound == true){
            finalPath = path;
            return;
        }
        if((startFound || destFound) && path.charAt(path.length()-1) == 'R'){
            path.deleteCharAt(path.length()-1);
        }

        if(startFound || destFound){
            path.append("U");
        }
    } 
 
} 
