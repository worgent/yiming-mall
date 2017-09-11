/*
 * Copyright (c) 2003 The Visigoth Software Society. All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Visigoth Software Society (http://www.visigoths.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. Neither the name "FreeMarker", "Visigoth", nor any of the names of the 
 *    project contributors may be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact visigoths@visigoths.org.
 *
 * 5. Products derived from this software may not be called "FreeMarker" or "Visigoth"
 *    nor may "FreeMarker" or "Visigoth" appear in their names
 *    without prior written permission of the Visigoth Software Society.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE VISIGOTH SOFTWARE SOCIETY OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Visigoth Software Society. For more
 * information on the Visigoth Software Society, please see
 * http://www.visigoths.org/
 */

package freemarker.template;

import java.util.List;

import freemarker.core.Environment;
import freemarker.template.utility.DeepUnwrap;

/**
 * A subinterface of {@link freemarker.template.TemplateMethodModel} that acts on models, rather
 * than on strings. {@link freemarker.template.TemplateMethodModel} interface will receive string
 * representations of its argument expressions, while this interface receives
 * the models themselves. The interface has no new methods. Instead, by 
 * implementing this interface the class declares that it wishes to receive 
 * actual TemplateModel instances in its arguments list when invoked instead of
 * their string representations. Further, if the implementation wishes to 
 * operate on POJOs that might be underlying the models, it can use the static 
 * utility methods in the {@link freemarker.template.utility.DeepUnwrap} class to easily obtain them.
 * @author Attila Szegedi, szegedia at users dot sourceforge dot net
 * @version $Id: TemplateMethodModelEx.java,v 1.8 2003/01/12 23:40:21 revusky Exp $
 */
public interface TemplateMethodModelEx extends TemplateMethodModel {

    /**
     * Executes a method call. 
     * @param arguments a <tt>List</tt> of {@link TemplateModel} objects
     * containing the values of the arguments passed to the method. If the 
     * implementation wishes to operate on POJOs that might be underlying the 
     * models, it can use the static utility methods in the {@link freemarker.template.utility.DeepUnwrap}
     * class to easily obtain them.
     * @return the return value of the method, or null. If the returned value
     * does not implement {@link TemplateModel}, it will be automatically 
     * wrapped using the {@link freemarker.core.Environment#getObjectWrapper() environment
     * object wrapper}.
     */
    public Object exec(List arguments) throws TemplateModelException;
}