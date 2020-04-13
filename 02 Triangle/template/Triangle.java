import java.util.Arrays;

class Triangle
{
    private int firstLength;
    private int secondLength;
    private int thirdLength;
    private TriangleType type;

    // Class to represent trinagles
    Triangle(int first, int second, int third)
    {
        firstLength = first;
        secondLength = second;
        thirdLength = third;
        type = identifyTriangleType(first, second, third);
    }

    // Returns the (previously indentified) type of this triangle
    TriangleType getType()
    {
        return type;
    }

    // Returns a printable string that describes this triangle
    public String toString()
    {
        return "(" + firstLength + "," + secondLength + "," + thirdLength + ")";
    }

    // Works out what kind of triangle this is !
    static TriangleType identifyTriangleType(int first, int second, int third)
    {
        
        TriangleType result = null;
        long side[] = new long[3];
        side[0] = first;
        side[1] = second;
        side[2] = third;
        Arrays.sort(side);
        for(int i = 0; i<3; i++)
        {
            if(side[i] <= 0)
            {
                result = TriangleType.Illegal;
                System.out.println("Illegal");
                return result;
            }
        }

        if(side[0] + side[1] > side[2])
        {
            if((side[2]*side[2]) == (side[1]*side[1]) + (side[0]*side[0]))
            {
                result = TriangleType.Right;
                System.out.println("Right");
            }
            else if(side[0] == side[1] && side[1]== side[2])
            {
                result = TriangleType.Equilateral;
                System.out.println("Equilateral");
            }
            else if(side[2] == side[1] || side[2] == side[0] || side[1] == side[0])
            {
                result = TriangleType.Isosceles;
                System.out.println("Isosceles");
            }
            else
            {
                result = TriangleType.Scalene;
                System.out.println("Scalene");
            }
        }
        else if(side[0] + side[1] == side[2])
        {
            result = TriangleType.Flat;
            System.out.println("Flat");
        }
        else
        {
            result = TriangleType.Impossible;
            System.out.println("Impossible");
        }
        return result;
    }
}
