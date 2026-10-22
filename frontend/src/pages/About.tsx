import sobreLightImage from "../assets/sobre-light.webp";
import sobreDarkImage from "../assets/sobre-dark.webp";
import "../styles/About.css";
import { HeaderHome } from "../components/HeaderHome";
import { FooterHome } from "../components/FooterHome";

export default function About() {

    return (
        <div className="about-page">
            <HeaderHome />

            <main className="about-content">
                <section className="about-copy" aria-labelledby="about-title">

                    <p className="about-eyebrow">Organize hoje. Planeje melhor.</p>
                    <h1 id="about-title">Sobre o CashFlow</h1>
                    <p>
                        O <strong>CashFlow</strong> é uma aplicação desenvolvida para
                        facilitar o gerenciamento das suas finanças pessoais. Com ela, você
                        pode cadastrar e acompanhar <strong>receitas e despesas</strong>,
                        organizar suas movimentações e ter uma visão mais clara da sua vida
                        financeira.
                    </p>
                </section>

                <div className="about-image-wrap">
                    <img
                        src={sobreLightImage}
                        alt="Celular com gráficos financeiros ao lado de uma carteira, moedas e uma lista"
                        className="about-image about-image--light"
                    />
                    <img
                        src={sobreDarkImage}
                        alt=""
                        aria-hidden="true"
                        className="about-image about-image--dark"
                    />
                </div>
            </main>
            <FooterHome />
        </div>
    );
}