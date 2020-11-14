import treenode.TreeNode;


public class MinSumRoot {
    private static int minSum;
    private static TreeNode minRoot;

    //找子树
    public static TreeNode findSubtree(TreeNode root){
        minSum=Integer.MAX_VALUE;
        minRoot=null;
        getSum(root);
        return minRoot;
    }

    //得到节点总数
    private static int getSum(TreeNode root){
        if(root == null){
            return 0;
        }

        int sum = getSum(root.left) +getSum(root.right) +root.val;
        if(sum<minSum){
            minSum=sum;
            minRoot=root;
        }
        return sum;
    }
    
    
    public static void main(String[] args){
    	//构建树
        String str = "[2,7,6,3,8,null,5]";
        TreeNode node = TreeNode.mkTree(str);
        //找子树
        System.out.println(findSubtree(node));
    }

}