import java.util.List;
import java.util.ArrayList;
import treenode.TreeNode;

//找所有的路径
public class AllPath {

	public static List<String> binaryTreePaths(TreeNode root){
        List<String> paths = new ArrayList<>();
        //判断是否根节点为空
        if(root==null){
            return paths;
        }

        //左子树
        List<String> leftPaths = binaryTreePaths(root.left);
        //右子树
        List<String> rightPaths = binaryTreePaths(root.right);

        //添加路径
        for(String path : leftPaths){
            paths.add(root.val + "->" + path);
        }
       //添加路径
        for(String path : rightPaths){
            paths.add(root.val + "->" + path);
        }

        if(paths.size() ==0){
            paths.add(""+root.val);
        }
        
        //返回路径
        return paths;
    }
	
	//主函数
    public static void main(String[] args){
    	//构建数
        String str = "[2,7,6,3,8,null,5]";
        TreeNode node = TreeNode.mkTree(str);
        List<String> paths=binaryTreePaths(node);
        //打印出路径
        for(int i = 0;i < paths.size();i++){
            System.out.println(paths.get(i));
        }
    }


}