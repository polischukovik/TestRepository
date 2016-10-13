package polischukovik.spring;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DocumentComposersExists implements Condition{

	@Override
	public boolean matches(ConditionContext ctx, AnnotatedTypeMetadata atm) {
		ClassLoader cl = ctx.getClassLoader();
		return false;
	}

}
