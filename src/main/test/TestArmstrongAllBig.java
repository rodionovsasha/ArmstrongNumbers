import com.github.rodionovsasha.ArmstrongNumbersBeyondLong;

import java.math.BigInteger;
import java.util.Arrays;

public class TestArmstrongAllBig {
    private static final BigInteger[] ALL_NUMBERS = {
            BigInteger.ONE,
            BigInteger.valueOf(2),
            BigInteger.valueOf(3),
            BigInteger.valueOf(4),
            BigInteger.valueOf(5),
            BigInteger.valueOf(6),
            BigInteger.valueOf(7),
            BigInteger.valueOf(8),
            BigInteger.valueOf(9),
            n("153"),
            n("370"),
            n("371"),
            n("407"),
            n("1634"),
            n("8208"),
            n("9474"),
            n("54748"),
            n("92727"),
            n("93084"),
            n("548834"),
            n("1741725"),
            n("4210818"),
            n("9800817"),
            n("9926315"),
            n("24678050"),
            n("24678051"),
            n("88593477"),
            n("146511208"),
            n("472335975"),
            n("534494836"),
            n("912985153"),
            n("4679307774"),
            n("32164049650"),
            n("32164049651"),
            n("40028394225"),
            n("42678290603"),
            n("44708635679"),
            n("49388550606"),
            n("82693916578"),
            n("94204591914"),
            n("28116440335967"),
            n("4338281769391370"),
            n("4338281769391371"),
            n("21897142587612075"),
            n("35641594208964132"),
            n("35875699062250035"),
            n("1517841543307505039"),
            n("3289582984443187032"),
            n("4498128791164624869"),
            n("4929273885928088826"),
            n("63105425988599693916"),
            n("128468643043731391252"),
            n("449177399146038697307"),
            n("21887696841122916288858"),
            n("27879694893054074471405"),
            n("27907865009977052567814"),
            n("28361281321319229463398"),
            n("35452590104031691935943"),
            n("174088005938065293023722"),
            n("188451485447897896036875"),
            n("239313664430041569350093"),
            n("1550475334214501539088894"),
            n("1553242162893771850669378"),
            n("3706907995955475988644380"),
            n("3706907995955475988644381"),
            n("4422095118095899619457938"),
            n("121204998563613372405438066"),
            n("121270696006801314328439376"),
            n("128851796696487777842012787"),
            n("174650464499531377631639254"),
            n("177265453171792792366489765"),
            n("14607640612971980372614873089"),
            n("19008174136254279995012734740"),
            n("19008174136254279995012734741"),
            n("23866716435523975980390369295"),
            n("1145037275765491025924292050346"),
            n("1927890457142960697580636236639"),
            n("2309092682616190307509695338915"),
            n("17333509997782249308725103962772"),
            n("186709961001538790100634132976990"),
            n("186709961001538790100634132976991"),
            n("1122763285329372541592822900204593"),
            n("12639369517103790328947807201478392"),
            n("12679937780272278566303885594196922"),
            n("1219167219625434121569735803609966019"),
            n("12815792078366059955099770545296129367"),
            n("115132219018763992565095597973971522400"),
            n("115132219018763992565095597973971522401")
    };

    public static void main(String[] args) {
        if (ALL_NUMBERS.length != 88) {
            throw new AssertionError("Unexpected test data length: " + ALL_NUMBERS.length);
        }

        BigInteger[] firstNumbers = ArmstrongNumbersBeyondLong.getNumbers(BigInteger.valueOf(1_000));
        BigInteger[] expectedFirstNumbers = Arrays.copyOfRange(ALL_NUMBERS, 0, 13);
        if (!Arrays.equals(expectedFirstNumbers, firstNumbers)) {
            throw new AssertionError(Arrays.toString(firstNumbers));
        }

        if (Boolean.getBoolean("fullArmstrongTest")) {
            BigInteger[] actual = ArmstrongNumbersBeyondLong.getNumbers();
            if (!Arrays.equals(ALL_NUMBERS, actual)) {
                throw new AssertionError(Arrays.toString(actual));
            }
        }

        System.out.println("OK");
    }

    private static BigInteger n(String number) {
        return new BigInteger(number);
    }
}
