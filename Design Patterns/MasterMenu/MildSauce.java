public class MildSauce extends ToppingDecorator {

    public MildSauce(TexMexInterface texMex) {
        super(texMex);
    }

    public String getSauce() {
        return super.getSauce() + " MildSauce,";
    }

    public double getCost() {
        return super.getCost() + 0.5;
    }

    public String getTexMex() {
        return "Base:" + super.getBase() + " with a choice of protein " + super.getProtein() + " topped with "
                + getToppings() + " with the following sauce " + getSauce();
    }
}