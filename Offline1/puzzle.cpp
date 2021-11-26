#include<bits/stdc++.h>
using namespace std;
int hamming(int** data,int k)
{
    int m=1;
    int hamming_dist=0;
    for(int i=0;i<k;i++)
    {
        for(int j=0;j<k;j++)
        {
           if(data[i][j]!=0&&abs(data[i][j]-m)!=0)
           {
               hamming_dist++;
           }
           m++;
        }

    }
    return hamming_dist;
}
int manhattan(int** data,int k)
{
    //int m=1;
    int manhattan_dist=0;
    for(int i=0;i<k;i++)
    {
        for(int j=0;j<k;j++)
        {
            int m=data[i][j];
        if(data[i][j]!=0)
           manhattan_dist+=abs(i-(int)((m-1)/k))+abs(j-(int)((m-1)%k));

        }

    }
    return manhattan_dist;
}

int linear_conflict(int** data,int k)
{
    int linear_conflict=0;
    for(int i=0;i<k;i++)
    {
        vector<int> row;
        for(int j=0;j<k;j++)
        {
            int m=data[i][j];
            if(i==(int)((m-1)/k)&&data[i][j]!=0)
            {
                row.push_back(m);
            }
        }
        for(int m=0;m<row.size();m++)
        {
            for(int p=m+1;p<row.size();p++)
            {
                if(row[m]>row[p])
                linear_conflict++;

            }
        }
        row.clear();

    }
    linear_conflict=manhattan(data,k)+linear_conflict*2;
    return linear_conflict;
}
class Node
{

private:
    int k;
    //int height;
    int  **data;
    int fval,gval,hval,choice;
    Node* parent;
public:
    Node(int**,int,int,int);
    int** get_data();
    Node* get_parent();
    void set_parent(Node*);
    int get_fval();
    int get_hval();
    int get_gval();
    void set_fval(int);
    void print_node();
    vector<Node*> get_children();
    bool equals(int**);
    ~Node();
};
Node::Node(int** s,int k,int l,int choice)
{
    this->k=k;
    data=new int*[k];
    gval=l;
    for(int i=0;i<k;i++)
    {
        data[i]=new int[k];
    }
    for(int i=0;i<k;i++)
    {
        for(int j=0;j<k;j++)
        {
             data[i][j]=s[i][j];
            //cout<<data[i][j]<<" ";
        }

    }
    parent=NULL;
    this->choice=choice;
    if(choice==1)
    {
        hval=hamming(data,k);
    }
    else if(choice==2)
    {
        hval=manhattan(data,k);
    }
    else
       hval=linear_conflict(data,k);

}
int** Node::get_data()
{
    return data;
}
Node* Node::get_parent()
{
    return parent;
}
int  Node::get_gval()
{
    return gval;
}
int  Node::get_fval()
{
    return fval;
}
int  Node::get_hval()
{
    return hval;
}
void Node::set_parent(Node* p)
{

    parent=p;
}
void Node:: set_fval(int f)
{
    fval=f;
}
Node::~Node()
{
    for(int i=0;i<k;i++)
    {
        delete data[i];
    }
    delete data;
}
void Node::print_node()
{
    for(int i=0;i<k;i++)
    {
        for(int j=0;j<k;j++)
        {
            if(data[i][j]==0)
                cout<<"* ";
            else
            cout<<data[i][j]<<" ";
        }
        cout<<endl;
    }
}
bool Node::equals(int ** arr)
{
    for(int i=0;i<k;i++)
    {
        for(int j=0;j<k;j++)
        {
            if(arr[i][j]!=data[i][j])
            {
               return false;
            }

        }
}
return true;
}
vector<Node*> Node::get_children()
{
    vector<Node*> v;
    int bl_x,bl_y;

    for(int i=0;i<k;i++)
    {
        for(int j=0;j<k;j++)
        {
            if(data[i][j]==0)
            {
                bl_x=i;
                bl_y=j;
            }

        }

    }
    if(bl_x-1>=0)
    {
    int **ch=new int*[k];
    for(int i=0;i<k;i++)
    {
        ch[i]=new int[k];
        for(int j=0;j<k;j++)
        {
            ch[i][j]=data[i][j];

        }

    }
    ch[bl_x][bl_y]=data[bl_x-1][bl_y];
    ch[bl_x-1][bl_y]=data[bl_x][bl_y];
    if(!parent->equals(ch))
    {
        Node* n=new Node(ch,k,gval+1,choice);
         n->set_parent(this);
        v.push_back(n);
    }

    }
    if(bl_x+1<k)
    {
    int **ch=new int*[k];
    for(int i=0;i<k;i++)
    {
        ch[i]=new int[k];
        for(int j=0;j<k;j++)
        {
            ch[i][j]=data[i][j];

        }

    }
    ch[bl_x][bl_y]=data[bl_x+1][bl_y];
    ch[bl_x+1][bl_y]=data[bl_x][bl_y];
    if(!parent->equals(ch))
    {
        Node* n=new Node(ch,k,gval+1,choice);
         n->set_parent(this);
        v.push_back(n);
    }

    }
    if(bl_y-1>=0)
    {
    int **ch=new int*[k];
    for(int i=0;i<k;i++)
    {
        ch[i]=new int[k];
        for(int j=0;j<k;j++)
        {
            ch[i][j]=data[i][j];

        }

    }
    ch[bl_x][bl_y-1]=data[bl_x][bl_y];
    ch[bl_x][bl_y]=data[bl_x][bl_y-1];
    if(!parent->equals(ch))
    {
        Node* n=new Node(ch,k,gval+1,choice);
        n->set_parent(this);
        v.push_back(n);
    }

    }
    if(bl_y+1<k)
    {
    int **ch=new int*[k];
    for(int i=0;i<k;i++)
    {
        ch[i]=new int[k];
        for(int j=0;j<k;j++)
        {
            ch[i][j]=data[i][j];

        }

    }
    ch[bl_x][bl_y]=data[bl_x][bl_y+1];
    ch[bl_x][bl_y+1]=data[bl_x][bl_y];
    if(!parent->equals(ch))
    {
        Node* n=new Node(ch,k,gval+1,choice);
         n->set_parent(this);
        v.push_back(n);
    }

    }
    return v;

}
int find_min(vector<Node*> l)
{

    int min_val=99999,min_idx;
    Node* min_node;
    for(int i=0;i<l.size();i++)
    {
        //Node* n=l->at(i);
        if(min_val>l[i]->get_fval())
        {
           // min_idx=i;
            min_val=l[i]->get_fval();
        }
    }
    return min_val;
}
class puzzle
{
    int n;
    vector<Node*> closed;
    vector<Node*> open;
    Node* src;
    Node* des;
public:
    puzzle(int,Node*);
    int A_star();
    void print();
    ~puzzle();

};
puzzle::puzzle(int n,Node* src)
{
    this->n=n;
    this->src=src;
    //closed=new vector<Node*>();
   // open=new vector<Node*>();
}
 void puzzle::print()
 {
     Node* temp=des;
     cout<<endl;
     while(temp!=NULL)
     {
         temp->print_node();
         cout<<endl;
         temp=temp->get_parent();
     }
 }
int puzzle::A_star()
{
    int moves=0;
    src->set_fval(0);
    int **dest;
    int v=1;
    for(int i=0;i<n;i++)
        for(int j=0;j<n;j++)
    {
        dest[i][j]=v;
        v++;
    }
    dest[n-1][n-1]=0;
    open.push_back(src);
    while(!open.empty())
    {
        moves++;
        int min_val=find_min(open);
        Node* q;
        //vector<int>::iterator min_idx;
        for (auto i = open.begin(); i != open.end(); ++i)
        {
        if(min_val==(*i)->get_fval())
        {
            q=(*i);
            open.erase(i);
            break;
        }

        }
        vector<Node*> children=q->get_children();
        for(int m=0;m<children.size();m++)
        {
            if(children[m]->equals(dest))
            {
                des=children[m];
                return moves;
            }

            children[m]->set_fval(children[m]->get_gval()+children[m]->get_hval());
            open.push_back(children[m]);
        }
        closed.push_back(q);


    }
    return moves;
}
int main()
{
    int k;
    cout<<"Enter size:";
    cin>>k;
    cout<<"Enter the grid: ";
    int **data;
     data=new int*[k];
    for(int i=0;i<k;i++)
    {
        data[i]=new int[k];
    }
    for(int i=0;i<k;i++)
    {
        for(int j=0;j<k;j++)
        {
            string s;
            cin>>s;
            if(s=="*")
                data[i][j]=0;
            else
                data[i][j]=stoi(s);

        }

    }
    cout<<"hamming: "<<hamming(data,k)<<endl;;
    cout<<"Manhattan: "<<manhattan(data,k)<<endl;;
    cout<<"Manhattan: "<<linear_conflict(data,k)<<endl;;
    //Node *n=new Node(data,k,0,1);
}


