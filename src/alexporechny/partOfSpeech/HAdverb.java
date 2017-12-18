package alexporechny.partOfSpeech;

/**
 * Наречие - Н -15
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class HAdverb extends WordForm {

    private final boolean isPronoun;

    public HAdverb(int myIndex) {
        this(myIndex, false);
    }

    public HAdverb(int myIndex, boolean isPronoun) {
        super(myIndex, 0, 0);
        this.isPronoun = isPronoun;
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Наречие";
        indexPtOfSp = 15;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        return false;
    }

    @Override
    public boolean isMainPS() {
        return false;
    }

    /**
     * @return возврящает true - если словоформа наречие-местоимение
     */
    public boolean getIsPronoun() {
        return isPronoun;
    }
}
