import java.util.List;
import java.util.ArrayList;
import treenode.TreeNode;

//�����е�·��
public class AllPath {

	public static List<String> binaryTreePaths(TreeNode root){
        List<String> paths = new ArrayList<>();
        //�ж��Ƿ���ڵ�Ϊ��
        if(root==null){
            return paths;
        }

        //������
        List<String> leftPaths = binaryTreePaths(root.left);
        //������
        List<String> rightPaths = binaryTreePaths(root.right);

        //���·��
        for(String path : leftPaths){
            paths.add(root.val + "->" + path);
        }
       //���·��
        for(String path : rightPaths){
            paths.add(root.val + "->" + path);
        }

        if(paths.size() ==0){
            paths.add(""+root.val);
        }
        
        //����·��
        return paths;
    }
	
	//������
    public static void main(String[] args){
    	//������
        String str = "[2,7,6,3,8,null,5]";
        TreeNode node = TreeNode.mkTree(str);
        List<String> paths=binaryTreePaths(node);
        //��ӡ��·��
        for(int i = 0;i < paths.size();i++){
            System.out.println(paths.get(i));
        }
    }


}