package org.jetbrains.teamcity;

import jetbrains.buildServer.controllers.BaseController;
import jetbrains.buildServer.controllers.interceptors.TeamCityHandlerInterceptor;
import jetbrains.buildServer.serverSide.TeamCityProperties;
import jetbrains.buildServer.web.impl.TeamCityInternalKeys;
import jetbrains.buildServer.web.openapi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GoogleTagManagerInterceptor implements TeamCityHandlerInterceptor {

    private static final String GTM_HEAD_URL = "/googleTagManagerPlugin/gtmHead.html";
    private static final String GTM_BODY_URL = "/googleTagManagerPlugin/gtmBody.html";

    private static final String EXTERNAL_PAGES_HEAD_EXTENSIONS_PARAM = TeamCityInternalKeys.INTERNAL_KEY_PREFIX + "allPagesHeadExtensions";
    private static final String EXTERNAL_PAGES_BODY_EXTENSIONS_PARAM = TeamCityInternalKeys.INTERNAL_KEY_PREFIX + "allPagesBodyExtensions";

    @NotNull
    private final GoogleTagManagerConfig config;


    public GoogleTagManagerInterceptor(@NotNull GoogleTagManagerConfig config,
                                       @NotNull WebControllerManager webControllerManager,
                                       @NotNull PluginDescriptor descriptor) {
        this.config = config;
        webControllerManager.registerController(GTM_HEAD_URL, new GtmController(descriptor, "googleTagManagerHead.jsp"));
        webControllerManager.registerController(GTM_BODY_URL, new GtmController(descriptor, "googleTagManagerBody.jsp"));
    }

    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response) {
        if (!isEnabled()) {
            return true;
        }

        String googleTagManagerContainerId = config.getGtmContainerId();
        if (googleTagManagerContainerId != null) {

            List<String> headExtensions = ((List<String>) request.getAttribute(EXTERNAL_PAGES_HEAD_EXTENSIONS_PARAM));
            if (headExtensions == null) {
                headExtensions = new CopyOnWriteArrayList<>();
                request.setAttribute(EXTERNAL_PAGES_HEAD_EXTENSIONS_PARAM, headExtensions);
            }
            List<String> bodyExtensions = (List<String>) request.getAttribute(EXTERNAL_PAGES_BODY_EXTENSIONS_PARAM);
            if (bodyExtensions == null) {
                bodyExtensions = new CopyOnWriteArrayList<>();
                request.setAttribute(EXTERNAL_PAGES_BODY_EXTENSIONS_PARAM, bodyExtensions);
            }
            headExtensions.add(GTM_HEAD_URL);
            bodyExtensions.add(GTM_BODY_URL);
        }
        return true;
    }

    private boolean isEnabled() {
        return TeamCityProperties.getBooleanOrTrue("teamcity.plugin.googleTagManager.enabled");
    }

    private class GtmController extends BaseController {

        private final PluginDescriptor descriptor;
        private final String jspName;

        private GtmController(@NotNull PluginDescriptor descriptor, @NotNull String jspName) {
            this.descriptor = descriptor;
            this.jspName = jspName;
        }

        @Nullable
        @Override
        protected ModelAndView doHandle(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse) throws Exception {
            ModelAndView view = new ModelAndView(descriptor.getPluginResourcesPath() + jspName);
            view.addObject("containerId", config.getGtmContainerId());
            return view;
        }
    }
}