package alexporechny.partOfSpeech;

/**
 * Неопределенные
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class Unknown extends WordForm {

    public Unknown(int myIndex, int intRod, int intPad, int intChis) {
        super(myIndex, intRod, intPad, intChis);
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Unknown";
        indexPtOfSp = 0;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        return false;
    }

    @Override
    public boolean isMainPS() {
        return false;
    }
}
