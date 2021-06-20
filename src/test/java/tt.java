import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

public class tt {

    public static void main(String[] args) {


        int N = 3;
        int ym[]={7,0,1,2,0,3,0,4,2,3,0,3,2,1,2,0,1,7,0,1};
        int lru[]={1,0,7};
        int len[]={0,0,0};
        int allchagetimes=0;
        for(int t=0;t<N;t++)
        {
//            cout<<lru[t]<<"  ";
            System.out.println(lru[t]);
        }
//        cout<<endl;

        for(int i=3;i<20;i++)
        {
            int flag=0;
            int j=0;
            int sum=0;
            for(;j<N;j++)
            {
                if(ym[i]==lru[j])
                {
                    flag=1;
                    break;
                }
                else if(ym[i]!=lru[j])
                {
                    sum++;
                    if(sum==3)
                    {
                        flag=0;
                        break;
                    }
                }


            }

            if(flag==1)
            {
                int temp=lru[0];
                if(j==0)
                {

                }
                else if(j==1)
                {
                    lru[0]=lru[j];

                    lru[2]=lru[2];
                    lru[1]=temp;
                }

                else if(j==2)
                {
                    lru[0]=lru[j];

                    lru[2]=lru[1];
                    lru[1]=temp;

                }

            }
            else if(flag==0)
            {
                allchagetimes++;
                lru[2]=lru[1];
                lru[1]=lru[0];
                lru[0]=ym[i];


            }

            for(int t=0;t<N;t++)
            {
                System.out.println(lru[t]);
            }

        }

        System.out.println(allchagetimes);


    }



}
