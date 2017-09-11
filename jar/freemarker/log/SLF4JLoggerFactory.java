package freemarker.log;

import org.slf4j.spi.LocationAwareLogger;

public class SLF4JLoggerFactory
		implements LoggerFactory
{
	public Logger getLogger(String category)
	{
		org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(category);
		if ((slf4jLogger instanceof LocationAwareLogger)) {
			return new LocationAwareSLF4JLogger((LocationAwareLogger)slf4jLogger);
		}
		return new LocationUnawareSLF4JLogger(slf4jLogger);
	}

	private static final class LocationAwareSLF4JLogger
			extends Logger
	{
		private static final String ADAPTER_FQCN = LocationAwareSLF4JLogger.class.getName();
		private final LocationAwareLogger logger;

		LocationAwareSLF4JLogger(LocationAwareLogger logger)
		{
			this.logger = logger;
		}

		public void debug(String message)
		{
			debug(message, null);
		}

		public void debug(String message, Throwable t)
		{
			this.logger.log(null, ADAPTER_FQCN, 10, message, t);
		}

		public void info(String message)
		{
			info(message, null);
		}

		public void info(String message, Throwable t)
		{
			this.logger.log(null, ADAPTER_FQCN, 20, message, t);
		}

		public void warn(String message)
		{
			warn(message, null);
		}

		public void warn(String message, Throwable t)
		{
			this.logger.log(null, ADAPTER_FQCN, 30, message, t);
		}

		public void error(String message)
		{
			error(message, null);
		}

		public void error(String message, Throwable t)
		{
			this.logger.log(null, ADAPTER_FQCN, 40, message, t);
		}

		public boolean isDebugEnabled()
		{
			return this.logger.isDebugEnabled();
		}

		public boolean isInfoEnabled()
		{
			return this.logger.isInfoEnabled();
		}

		public boolean isWarnEnabled()
		{
			return this.logger.isWarnEnabled();
		}

		public boolean isErrorEnabled()
		{
			return this.logger.isErrorEnabled();
		}

		public boolean isFatalEnabled()
		{
			return this.logger.isErrorEnabled();
		}
	}

	private static class LocationUnawareSLF4JLogger
			extends Logger
	{
		private final org.slf4j.Logger logger;

		LocationUnawareSLF4JLogger(org.slf4j.Logger logger)
		{
			this.logger = logger;
		}

		public void debug(String message)
		{
			this.logger.debug(message);
		}

		public void debug(String message, Throwable t)
		{
			this.logger.debug(message, t);
		}

		public void info(String message)
		{
			this.logger.info(message);
		}

		public void info(String message, Throwable t)
		{
			this.logger.info(message, t);
		}

		public void warn(String message)
		{
			this.logger.warn(message);
		}

		public void warn(String message, Throwable t)
		{
			this.logger.warn(message, t);
		}

		public void error(String message)
		{
			this.logger.error(message);
		}

		public void error(String message, Throwable t)
		{
			this.logger.error(message, t);
		}

		public boolean isDebugEnabled()
		{
			return this.logger.isDebugEnabled();
		}

		public boolean isInfoEnabled()
		{
			return this.logger.isInfoEnabled();
		}

		public boolean isWarnEnabled()
		{
			return this.logger.isWarnEnabled();
		}

		public boolean isErrorEnabled()
		{
			return this.logger.isErrorEnabled();
		}

		public boolean isFatalEnabled()
		{
			return this.logger.isErrorEnabled();
		}
	}
}