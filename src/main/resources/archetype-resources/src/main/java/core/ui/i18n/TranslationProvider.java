package ${package}.core.ui.i18n;

import com.vaadin.flow.i18n.DefaultI18NProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/**
 * All the texts are written in English. This I18NProvider only translates messages when
 * the Locale is not English.
 */
@Component
public class TranslationProvider extends DefaultI18NProvider {

	private static final List<Locale> PROVIDED_LOCALES = List.of(Locale.ENGLISH, Locale.GERMAN);

	public TranslationProvider() {
		super(PROVIDED_LOCALES);
	}

}
