package aleph;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ContextUtil.context = applicationContext;

	}

	public static <T> T getBean(Class<T> type) {
		return getContext().getBean(type);
	}

}