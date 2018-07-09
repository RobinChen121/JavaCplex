package deterministic.singleitem;



import java.util.Arrays;

/**
 * @author chen
 * WW �㷨
 *
 */
public class SingleItemLS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int T=12; //����
		double[] D={10,62,12,130,154,129,88,52,124,160,238,41}; 
		double[] s={54,54,54,54,54,54,54,54,54,54,54,54};
		double[] h={0.4,0.4,0.4,0.4,0.4,0.4,0.4,0.4,0.4,0.4,0.4,0.4};
		double[] v = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		
		double[][] cost = new double[T][T];//�����������ڼ�ĳɱ�����һ��������������������������һ������
		double[] OptCost = new double[T]; //��¼�ӵ�һ�ڵ���T�ڵ������ܳɱ�
		int[] Order = new int[T];  //�ӵ�һ�ڵ���T�ڵ������������У���0-1��ʾ��0��ʾ���ڲ�������1��ʾ������������	
		double[] I = new double[T]; //��¼ÿ�׶εĿ��

		for (int i = 0; i < T; i++)
			for (int j = 0; j < T; j++)
				cost[i][j]=Double.MAX_VALUE;  //���Ի��ɱ�


		//�������������ڵĳɱ����Լ������ܳɱ�����
		for (int i = 0; i < T; i++)
		{
			if(i > 0)  //��¼�ӵ�1�ڵ���i-1�ڵ������ܳɱ�
			{
				double[] p = new double[T];
				for (int j=0;j<T;j++)
					p[j]=cost[j][i-1];
				OptCost[i-1]= Arrays.stream(p).min().getAsDouble();
			}

			for (int j = i; j < T; j++)
			{
				double sum=0;
				for (int k = i; k < j + 1; k++)
					sum += D[k];
				double hSum = 0; double tempSum = sum;
				for (int k = i; k < j + 1; k++)
				{	
					hSum = hSum + h[i]*(sum - D[k]);
					sum = sum - D[k];
				}
				if (i>0)
					cost[i][j] = OptCost[i-1] + hSum + s[i] + v[i] * tempSum;//�õ���i�ڵ���j�ڵ������ܳɱ�
				else
					cost[i][j] = hSum + s[i]+ v[i] * tempSum;
			}
		}
		double[] p = new double[T];
		for (int j = 0; j < T; j++)
				p[j] = cost[j][T-1];
		OptCost[T-1] = Arrays.stream(p).min().getAsDouble();

		//�������������У��Ӻ���ǰ��
		int i=T-1;
		while (i>=0)
		{
			if (OptCost[i] == cost[i][i])
			{
				Order[i] = 1;
				i = i-1;
			}
			else
			{
				Order[i] = 0;
				int index = i;
				for (int k = 0; k < i; k++)
				{
					if (OptCost[i] == cost[k][i])
					{
						index = k;
						Order[index] = 1;
						break;
					}
				}
				Order[index] = 1;
				for (int k = index + 1; k < i; k++)
					Order[k] = 0;
				i = index - 1;
			}
		}

		//���������������еõ�ÿ���׶εĿ�棬��ǰ�����
		i=0;
		int index = 0;
		while (index < T-1)
		{		
			for (int j = i + 1; j < T; j++)
			{
				if (Order[j] == 1)
				{
					index = j;
					break;
				}
				if (j == T-1 && Order[j] == 0)
				{
					index = T;
				}
			}
			double sum = 0;
			for (int k = i; k < index; k++)
				sum += D[k];
			for (int k = i; k < index; k++)
			{
				sum = sum - D[k];
				I[k] = sum;
			}
			i = index;
		}
		
		System.out.println("������������:");
		System.out.println(Arrays.toString(Order));
		System.out.println("�ӵ�1�ڵ����ڵ������ܳɱ�:");
		System.out.println(Arrays.toString(OptCost));
		System.out.println("��������ʱ�ĸ��׶ο��ˮƽλ:");
		System.out.println(Arrays.toString(I));
	}
}
