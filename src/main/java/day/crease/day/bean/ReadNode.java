package day.crease.day.bean;

/**
 * 模拟红黑树插入
 */
public class ReadNode {

    // 红色节点
    private final int R = 0;
    // 黑色节点
    private final int B = 1;
    // 根节点
    private Node root = null;

    /**
     * 节点属性
     */
    class Node{
        int data;
        // 所有插入的节点默认为红色
        int color = R;
        // 左节点
        Node left;
        // 右节点
        Node right;
        // 父节点
        Node parent;

        public Node(int data) {
            this.data = data;
        }
    }

    /**
     * 把data插入到红黑树上，默认父节点是存在的
     * @param node
     * @param data
     */
    public void insert(Node node,int data){
        if(data > node.data){
            if(node.right == null){
                node.right = new Node(data);
            }else{
                insert(node.right,data);
            }
        }else{
            if(node.left == null){
                node.left = new Node(data);
            }else {
                insert(node.left,data);
            }
        }
    }

    /**
     * 模拟左旋
     * @param node
     */
    public void leftRotate(Node node){
        if(node.parent == null){
            Node E = root;
            Node S = E.right;

            // 第一步，移动S的左子树指向E
            E.right = S.left;
            S.left.parent = E;

            // 第二步，修改E的指针
            E.parent = S;

            //第三步，修改S的指针
            S.parent = null;
        }
    }

    /**
     * 模拟右旋
     * @param node
     */
    public void rightRotate(Node node){
        if(node.parent == null){
            Node E = root;
            Node S = E.left;

            // 同样，第一步移动S的右子树指向E
           S.right = E.left;
           S.right.parent = E;

           // 第二步，修改S的指针
            S.parent = null;

            //第三步，修改E的只针
            E.parent = S;


        }
    }
}
