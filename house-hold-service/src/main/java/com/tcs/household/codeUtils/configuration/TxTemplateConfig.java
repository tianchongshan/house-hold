package com.tcs.household.codeUtils.configuration;


import com.tcs.household.codeUtils.anno.ConditionalOnClass;
import com.tcs.household.codeUtils.persistent.dao.CodeLogRepository;
import com.tcs.household.codeUtils.persistent.dao.CodeRepository;
import com.tcs.household.codeUtils.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 
 * @author xiaoj
 *
 */
@Configuration
@ConditionalOnClass({PlatformTransactionManager.class})
public class TxTemplateConfig {

	@Autowired
	private PlatformTransactionManager txManager;

    @Bean("txTemplate_require_new")
    public TransactionTemplate txTemplate() {
    	TransactionDefinition definition = new TransactionDefinition() {
			
			@Override
			public boolean isReadOnly() {
				return false;
			}
			
			@Override
			public int getTimeout() {
				return TransactionDefinition.TIMEOUT_DEFAULT;
			}
			
			@Override
			public int getPropagationBehavior() {
				return TransactionDefinition.PROPAGATION_REQUIRES_NEW;
			}
			
			@Override
			public String getName() {
				return "txTemplate_require_new";
			}
			
			@Override
			public int getIsolationLevel() {
				return TransactionDefinition.ISOLATION_REPEATABLE_READ;
			}
		};
    	
    	TransactionTemplate template = new TransactionTemplate(txManager, definition);
    	return template;
    }


	@Primary
	@Bean("txTemplate_required")
	public TransactionTemplate txTemplateNormal() {
		TransactionDefinition definition = new TransactionDefinition() {

			@Override
			public boolean isReadOnly() {
				return false;
			}

			@Override
			public int getTimeout() {
				return TransactionDefinition.TIMEOUT_DEFAULT;
			}

			@Override
			public int getPropagationBehavior() {
				return TransactionDefinition.PROPAGATION_REQUIRED;
			}

			@Override
			public String getName() {
				return "txTemplate_require_new";
			}

			@Override
			public int getIsolationLevel() {
				return TransactionDefinition.ISOLATION_REPEATABLE_READ;
			}
		};

		TransactionTemplate template = new TransactionTemplate(txManager, definition);
		return template;
	}

	@Bean
	public CodeLogRepository codeLogRepository() {
    	return new CodeLogRepository();
	}

	@Bean
	public CodeRepository codeRepository() {
		return new CodeRepository();
	}

	@Bean
	public CodeService codeService() {
		return new CodeService();
	}

}
