package org.reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.owasp.html.Sanitizers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;


public class Main {

    public static void main(String[] args) {


        String htmlText = "<p><em><a title=\"Samples\" href=\"http://cultiz.com/blog/category/musique/2-sample-or-not-2-sample/\">On en a déjà parlé sur Cultiz</a>, les samples jouent un rôle essentiel dans la plupart des instrus de rap. Qui n’a jamais à la première écoute d’un morceau bloqué sur une mélodie familière en se demandant, « mais où est-ce que j’ai déjà entendu ça ? »…<span id=\"more-14750\"></span></em></p> <p>.<br /> <!--more--></p> <p style=\"text-align: center;\">Et comme on aime bien avoir votre avis, et qu’on aime assez <em>disserter sur les concepts d’art, de propriété intellectuelle, de subjectivité, de sampleur sachant sampler sans son sample ou non, tout ça, on vous le demande</em></p> <h3 style=\"text-align: center;\">« Alors, 2";
        String cleanedText = removeHtmlTags(htmlText);
        System.out.println(cleanedText);
    }

    public static String removeHtmlTags( String html ) {
        Document doc = Jsoup.parse(html);
        String text = doc.text();
        return text;
    }
}