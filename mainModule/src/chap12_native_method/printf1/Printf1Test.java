class Printf1Test
{
    public static void main(String[] args)
    {
        int count = Printf1.print(8,4,3.14);// now count is 8,because parameter width is 8,so will print 8 characters
	count += Printf1.print(8,4,count);  // now count is 16,because parameter width is still 8,so will print 8 characters
	System.out.println();

	for(int i =  0;i < count; i++)
	{
	    System.out.print("-");// print 16 characters "-"
	}

	System.out.println();
    }
}
