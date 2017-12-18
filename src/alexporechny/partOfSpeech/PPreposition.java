package alexporechny.partOfSpeech;

/**
 * Предлог - Р - 17
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class PPreposition extends WordForm {

    public PPreposition(int myIndex, int intRod, int intPad) {
        super(myIndex, intRod, intPad, 0);
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Предлог";
        indexPtOfSp = 17;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        switch (child.indexPtOfSp) {
            case 1:
                if (child.myIndex > myIndex && child.intPad == intPad) {
                    ((SNoun) child).setHavePreposition(true);
                    return true;
                }
            default:
                return false;
        }
    }

    @Override
    public boolean isMainPS() {
        return false;
    }
}
