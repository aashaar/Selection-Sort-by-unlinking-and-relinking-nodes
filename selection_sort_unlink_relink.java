/*
 ******************************************************************************
 * Title: Seelction sort by re-linking i.e. node swapping
 * Author : Aashaar Panchalan
 * Date : 30-Jan-2018
 * Description :
 *   - Generally in selection sort, we swap values of the nodes.
 *   - But here, instead of swapping the values, we are unlinking and re-linking the nodes
 *   - i.e. repositioning the entire node, instead of just swapping the values.
 *   - List has to be sorted in descending order.
 ******************************************************************************  */


public class selection_sort_unlink_relink
{
    static Node head;

    public static void main(String[] args)
    {

        insert(new Node(5,null));
        insert(new Node(21,null));
        insert(new Node(7,null));
        insert(new Node(10,null));
        insert(new Node(12,null));
        insert(new Node(26,null));
        insert(new Node(2,null));
        insert(new Node(78,null));
        insert(new Node(105,null));
        insert(new Node(54,null));
        insert(new Node(93,null));
        insert(new Node(61,null));

        System.out.println("List before sorting :");
        traverseList(head);
        Node maxHead = head;
        head = selectionSortUnlinkRelink(head,maxHead);
        System.out.println("List after sorting :");
        traverseList(head);
        System.out.println("*********** Execution completed ************");
    }

    /*
    ******************************************************************************
    * Function selectionSortUnlinkRelink :
    * Input parameters :
        - Node headNode - Head of the unsorted linked list in the ongoing iteration for bubble sort
        - Node maxHead - Head of the linked list after sorting (global max node)
    * Output parameter :
        - Node maxHead - return maxHead at the end and assign its value to the head of the linked list in main function
    ******************************************************************************     */
    public static Node selectionSortUnlinkRelink(Node headNode,Node maxHead)
    {
        if (headNode == null)
        {
            return headNode;
        }
        else
        {
            int max = headNode.getData();
            Node maxNode = headNode;
            Node current = headNode.getNext();
            while(current != null)
            {
                int temp = current.getData();
                if(temp > max)
                {
                    maxNode = current;
                    max = temp;
                }
                current = current.getNext();
            }


            //node unnlink-relink logic starts
            /*
            Relink sequence :
            1]head.next = max.next
            2]max.next = temp (temp=head.next)
            3]prevHead.next = max (as prev of the head should now point to max)
            4]prevMax.next = head (similarly prev of max should now point to head for swapping to be complete)

            */
            Node prevMaxNode = getPreviousNode(head, max); // to get previous node of the max node
            Node prevHeadNode = getPreviousNode(head, headNode.getData()); // to get previous node of the head node of the current iteration of selection sort
            Node tempNode = headNode.getNext(); // next node of the head node

            if(maxNode == tempNode) // if max node is right next to the head node - prevMaxNode and temp node are one & the same
            {
                headNode.setNext(maxNode.getNext()); // [1]
                if(prevHeadNode != null)
                {
                    maxNode.setNext(headNode); // [2] they were next to each other, hence swapping them
                    prevHeadNode.setNext(maxNode); // [3]
                }
            }
            else
            {
                headNode.setNext(maxNode.getNext()); // [1]
                if(prevMaxNode != null)
                {
                    prevMaxNode.setNext(headNode);  // [4]
                }
                maxNode.setNext(tempNode);
                if(prevHeadNode != null)  // [2]
                {
                    prevHeadNode.setNext(maxNode); // [3]
                }
                head = maxNode;
                headNode = maxNode.getNext();
                if(head.getData() > maxHead.getData())
                {
                    maxHead = head; // if the max in this iteration is the global max store it to maxHead to return the head later
                }
            }
        }
        selectionSortUnlinkRelink(headNode,maxHead);
        return maxHead; // to bring head back to it's original place, else post-swapping the head won't be pointing to the new head.
    }

    public static Node getPreviousNode(Node node, int val)
    {
        if(node.getNext() == null) // last node
        {
            return null;
        }
        else if (node.getData() == val) // if node is head
        {
            return null;
        }
        else if (node.getNext().getData() == val) // previous node of the node - val
        {
            return node;
        }
        else
        {
            return getPreviousNode(node.getNext(),val); //recursively call until we find the node
        }
    }

    public static void insert(Node newNode)
    {
        newNode.setNext(head);
        head = newNode;
    }

    public static Node traverseList(Node head)
    {
        if(head == null)
        {
            return null;
        }
        else
        {
            System.out.println(head.getData());
            return traverseList(head.getNext());
        }
    }
}
